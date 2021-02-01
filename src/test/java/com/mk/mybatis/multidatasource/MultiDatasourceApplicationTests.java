package com.mk.mybatis.multidatasource;

import com.mk.mybatis.multidatasource.base.dao.DaseDao;
import com.mk.mybatis.multidatasource.one.dao.ManDao;
import com.mk.mybatis.multidatasource.two.dao.PersonDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiDatasourceApplicationTests {

    @Resource
    private DaseDao baseDao;

    @Resource
    private PersonDao personDao;

    @Resource
    private ManDao manDao;

    @Test
    public void testBaseDao() throws InterruptedException {
          System.out.println(baseDao.selectByPrimaryKey(1));
          Thread.sleep(10000);
    }


    @Test
    public void testPersonDao() {
        System.out.println(personDao.selectByPrimaryKey(1));
    }


    @Test
    public void testManDao() {
        System.out.println(manDao.selectByPrimaryKey(1));
    }

}
