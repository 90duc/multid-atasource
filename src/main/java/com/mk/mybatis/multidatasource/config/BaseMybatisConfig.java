package com.mk.mybatis.multidatasource.config;

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
@MapperScan(value = "com.mk.mybatis.multidatasource.base.dao", sqlSessionTemplateRef = "baseSqlSessionTemplate")
public class BaseMybatisConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties baseDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean("datasource")
    public DataSource baseDataSource(@Qualifier("baseDataSourceProperties") DataSourceProperties dataSourceProperties) {

        DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().build();
        return dataSource;
    }


    @Primary
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public MybatisProperties baseMybatisProperties() {
        return new MybatisProperties();
    }

    @Primary
    @Bean
    public SqlSessionFactory baseSqlSessionFactory(@Qualifier("datasource") DataSource dataSource,
                                                   @Qualifier("baseMybatisProperties") MybatisProperties mybatisProperties) throws Exception {
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


    @Primary
    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("datasource") DataSource dataSource) {

        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean
    public SqlSessionTemplate baseSqlSessionTemplate(@Qualifier("baseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}