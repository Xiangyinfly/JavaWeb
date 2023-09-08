package com.mySSM.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.SQLException;

import com.mySSM.transaction.transactionManager;

@WebFilter("*.do")
public class openSessionInViewFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            transactionManager.beginTrans();
            filterChain.doFilter(servletRequest,servletResponse);
            transactionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                transactionManager.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
    }
}
