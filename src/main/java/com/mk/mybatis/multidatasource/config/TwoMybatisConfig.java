package com.mk.mybatis.multidatasource.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
@MapperScan(value = "com.mk.mybatis.multidatasource.two.dao", sqlSessionTemplateRef = "twoSqlSessionTemplate")
@Slf4j
public class TwoMybatisConfig {

        @Bean
    @ConfigurationProperties(prefix = "sys.two-mybatis.datasource")
    public DataSourceProperties twoDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource twoDataSource(@Qualifier("twoDataSourceProperties") DataSourceProperties dataSourceProperties) {
        DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().build();
        return dataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "sys.two-mybatis.mybatis")
    public MybatisProperties twoMybatisProperties() {
        return new MybatisProperties();
    }

    @Bean
    public SqlSessionFactory twoSqlSessionFactory(@Qualifier("twoDataSource") DataSource dataSource,
                                                  @Qualifier("twoMybatisProperties") MybatisProperties mybatisProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(mybatisProperties.resolveMapperLocations());
        bean.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
        bean.setConfigurationProperties(mybatisProperties.getConfigurationProperties());
        bean.setConfiguration(mybatisProperties.getConfiguration());
        bean.setConfigLocation(Optional.ofNullable(mybatisProperties.getConfigLocation()).map(location -> {
            try {
                ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
                return resourceResolver.getResource(location);
            } catch (Exception var3) {
                return null;
            }
        }).orElse(null));

        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager twoTransactionManager(@Qualifier("twoDataSource") DataSource dataSource) {

        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate twoSqlSessionTemplate(@Qualifier("twoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}