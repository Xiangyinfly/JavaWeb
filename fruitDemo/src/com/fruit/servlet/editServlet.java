package com.fruit.servlet;

import com.fruit.domain.Fruit;
import com.mySSM.mySpringMVC.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.utils.stringUtil;
import com.fruit.dao.impl.fruitDaoImpl;
import com.fruit.dao.fruitDao;

@WebServlet("/edit.do")
public class editServlet extends ViewBaseServlet {
    private fruitDao fruitDao = new fruitDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if (stringUtil.isNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = fruitDao.getFruitById(fid);
            req.setAttribute("fruit",fruit);
            super.processTemplate("edit",req,resp);
        }
    }
}
