package com.fruit.controller;

import com.fruit.dao.impl.fruitDaoImpl;
import com.fruit.domain.Fruit;
import com.mySSM.mySpringMVC.ViewBaseServlet;
import com.utils.stringUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;


public class fruitController extends ViewBaseServlet {


    private fruitDaoImpl fruitDao = new fruitDaoImpl();

    private String index(String operate,String keyword,Integer page,HttpServletRequest req) {
        HttpSession session = req.getSession();

        if (page == null) {
            page = 1;
        }

        if (stringUtil.isNotEmpty(operate) && operate.equals("search")) {//此处说明是通过查询进入的
            page = 1;
            if (stringUtil.isEmpty(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword",keyword);
        } else {//此处说明并非通过查询进入，如直接在地址栏访问
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            } else {
                keyword = "";
            }
        }

        int totalPage = (fruitDao.getTotalCount(keyword) - 1) / 5 + 1;
        session.setAttribute("totalPage",totalPage);

        session.setAttribute("page",page);

        List<Fruit> fruitList = fruitDao.getFruitList(page,keyword);
        session.setAttribute("fruitList",fruitList);
        //super.processTemplate("index",req,resp);
        return "index";
    }

    private String add(String fname,Integer fprice,Integer fcount,String fremark) {
        fruitDao.addFruit(new Fruit(0,fname,fprice,fcount,fremark));
        //resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }

    private String del(Integer fid) {
        if (fid != null) {
            fruitDao.deleteFruit(fid);
            //resp.sendRedirect("fruit.do");
            return "redirect:fruit.do";
        }
        return "error";
    }

    private String edit(Integer fid,HttpServletRequest req) {
        if (fid != null) {
            Fruit fruit = fruitDao.getFruitById(fid);
            req.setAttribute("fruit",fruit);
            //super.processTemplate("edit",req,resp);
            return "edit";
        }
        return "error";
    }

    private String update(Integer fid,String fname,Integer fprice,Integer fcount,String fremark) {
        fruitDao.updateFruit(new Fruit(fid,fname,fprice,fcount,fremark));
        //resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }
}
