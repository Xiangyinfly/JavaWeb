package com.fruit.controller;

import com.fruit.dao.impl.fruitDaoImpl;
import com.fruit.pojo.Fruit;
import com.mySSM.mySpringMVC.ViewBaseServlet;
import com.utils.stringUtil;
import com.fruit.service.impl.fruitServiceImpl;
import com.fruit.service.fruitService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


public class fruitController extends ViewBaseServlet {


    private fruitService fruitService = null;

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

        int totalPage = fruitService.getPageCount(keyword);
        session.setAttribute("totalPage",totalPage);

        session.setAttribute("page",page);

        List<Fruit> fruitList = fruitService.getFruitList(keyword,page);
        session.setAttribute("fruitList",fruitList);
        //super.processTemplate("index",req,resp);
        return "index";
    }

    private String add(String fname,Integer fprice,Integer fcount,String fremark) {
        fruitService.addFruit(new Fruit(0,fname,fprice,fcount,fremark));
        //resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }

    private String del(Integer fid) {
        if (fid != null) {
            fruitService.deleteFruit(fid);
            //resp.sendRedirect("fruit.do");
            return "redirect:fruit.do";
        }
        return "error";
    }

    private String edit(Integer fid,HttpServletRequest req) {
        if (fid != null) {
            Fruit fruit = fruitService.getFruitById(fid);
            req.setAttribute("fruit",fruit);
            //super.processTemplate("edit",req,resp);
            return "edit";
        }
        return "error";
    }

    private String update(Integer fid,String fname,Integer fprice,Integer fcount,String fremark) {
        fruitService.updateFruit(new Fruit(fid,fname,fprice,fcount,fremark));
        //resp.sendRedirect("fruit.do");
        return "redirect:fruit.do";
    }
}
