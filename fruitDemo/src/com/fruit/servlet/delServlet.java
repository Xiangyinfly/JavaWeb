package com.fruit.servlet;

import com.fruit.dao.impl.fruitDaoImpl;
import com.mySSM.mySpringMVC.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fruit.dao.*;
import com.utils.stringUtil;

import java.io.IOException;


@WebServlet("/del.do")
public class delServlet extends ViewBaseServlet {
    private fruitDao fruitDao = new fruitDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if (stringUtil.isNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            fruitDao.deleteFruit(fid);
            resp.sendRedirect("index");
        }
    }
}
