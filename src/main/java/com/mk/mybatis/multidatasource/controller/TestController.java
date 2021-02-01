package com.mk.mybatis.multidatasource.controller;

import com.mk.mybatis.multidatasource.base.dao.DaseDao;
import com.mk.mybatis.multidatasource.base.entity.Base;
import com.mk.mybatis.multidatasource.one.dao.ManDao;
import com.mk.mybatis.multidatasource.two.dao.PersonDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Objects;

@Controller
public class TestController {

    public static class Res{

        public static final int SUCCESS = 0;
        public static final int FAILURE = 1;

        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public static Res fail(String msg) {
            Res r = new Res();
            r.code = FAILURE;
            r.msg =msg;
            return r;
        }
    }

    @Resource
    private DaseDao baseDao;

    @Resource
    private PersonDao personDao;
    @Resource
    private ManDao manDao;

    @RequestMapping(value = "/base")
    @ResponseBody
    public Res base() {
        Base base = baseDao.selectByPrimaryKey(1);
        return Res.fail(Objects.toString(base));
    }

}
