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

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private String index(HttpServletRequest req) throws IOException {
        req.setCharacterEncoding("utf-8");
        int page = 1;
        HttpSession session = req.getSession();
        String keyword = null;

        String operate = req.getParameter("operate");
        if (stringUtil.isNotEmpty(operate) && operate.equals("search")) {//此处说明是通过查询进入的
            keyword = req.getParameter("keyword");
            if (stringUtil.isEmpty(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword",keyword);
        } else {//此处说明并非通过查询进入，如直接在地址栏访问
            String pageStr = req.getParameter("page");
            if (stringUtil.isNotEmpty(pageStr)) {
                page = Integer.parseInt(pageStr);
            }

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

    private String add(HttpServletRequest req) throws IOException {
        req.setCharacterEncoding("utf-8");
        String fname = req.getParameter("fname");
        String fpriceStr = req.getParameter("fprice");
        int fprice = Integer.parseInt(fpriceStr);
        String fcountStr = req.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);
        String fremark = req.getParameter("fremark");

        fruitDao.addFruit(new Fruit(0,fname,fprice,fcount,fremark));
        //resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }

    private String del(HttpServletRequest req) {
        String fidStr = req.getParameter("fid");
        if (stringUtil.isNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            fruitDao.deleteFruit(fid);
            //resp.sendRedirect("fruit.do");
            return "redirect:fruit.do";
        }
        return "error";
    }

    private String edit(HttpServletRequest req) {
        String fidStr = req.getParameter("fid");
        if (stringUtil.isNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = fruitDao.getFruitById(fid);
            req.setAttribute("fruit",fruit);
            //super.processTemplate("edit",req,resp);
            return "edit";
        }
        return "error";
    }

    private String update(HttpServletRequest req) throws IOException {
        req.setCharacterEncoding("utf-8");
        String fidStr = req.getParameter("fid");
        int fid = Integer.parseInt(fidStr);
        String fname = req.getParameter("fname");
        String fpriceStr = req.getParameter("fprice");
        int fprice = Integer.parseInt(fpriceStr);
        String fcountStr = req.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);
        String fremark = req.getParameter("fremark");

        fruitDao.updateFruit(new Fruit(fid,fname,fprice,fcount,fremark));
        //resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }
}
