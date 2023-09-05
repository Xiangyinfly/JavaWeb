package com.fruit.servlet;

import com.fruit.domain.Fruit;
import com.mySSM.mySpringMVC.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.fruit.dao.impl.fruitDaoImpl;

@WebServlet("/update.do")
public class updateServlet extends ViewBaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String fidStr = req.getParameter("fid");
        int fid = Integer.parseInt(fidStr);
        String fname = req.getParameter("fname");
        String fpriceStr = req.getParameter("fprice");
        int fprice = Integer.parseInt(fpriceStr);
        String fcountStr = req.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);
        String fremark = req.getParameter("fremark");

        fruitDaoImpl fruitDao = new fruitDaoImpl();
        fruitDao.updateFruit(new Fruit(fid,fname,fprice,fcount,fremark));
        resp.sendRedirect("index");
    }
}
