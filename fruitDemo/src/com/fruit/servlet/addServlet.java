package com.fruit.servlet;

import com.fruit.dao.impl.fruitDaoImpl;
import com.fruit.domain.Fruit;
import com.mySSM.mySpringMVC.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add.do")
public class addServlet extends ViewBaseServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String fname = req.getParameter("fname");
        String fpriceStr = req.getParameter("fprice");
        int fprice = Integer.parseInt(fpriceStr);
        String fcountStr = req.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);
        String fremark = req.getParameter("fremark");

        fruitDaoImpl fruitDao = new fruitDaoImpl();
        fruitDao.addFruit(new Fruit(0,fname,fprice,fcount,fremark));
        resp.sendRedirect("index");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processTemplate("add",req,resp);
    }
}
