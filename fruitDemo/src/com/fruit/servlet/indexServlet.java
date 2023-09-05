package com.fruit.servlet;

import com.fruit.dao.fruitDao;
import com.fruit.dao.impl.fruitDaoImpl;
import com.fruit.domain.Fruit;
import com.mySSM.mySpringMVC.ViewBaseServlet;
import com.utils.stringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class indexServlet extends ViewBaseServlet {
    private fruitDaoImpl fd = new fruitDaoImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        int page = 1;
        HttpSession session = req.getSession();
        String keyword = null;

        String operate = req.getParameter("operate");
        if (stringUtil.isNotEmpty(operate) && operate.equals("search")) {//此处说明是通过查询进入的，keyword从
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

        int totalPage = (fd.getTotalCount(keyword) - 1) / 5 + 1;
        session.setAttribute("totalPage",totalPage);

        session.setAttribute("page",page);

        List<Fruit> fruitList = fd.getFruitList(page,keyword);
        session.setAttribute("fruitList",fruitList);
        super.processTemplate("index",req,resp);
    }
}
