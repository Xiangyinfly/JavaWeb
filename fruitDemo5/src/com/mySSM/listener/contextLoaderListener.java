package com.mySSM.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mySSM.ioc.classPathXmlApplicationContext;
import com.mySSM.ioc.beanFactory;

@WebListener
public class contextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext application = servletContextEvent.getServletContext();
        String path = application.getInitParameter("contextConfigPath");
        beanFactory beanFactory = new classPathXmlApplicationContext(path);
        application.setAttribute("beanFactory",beanFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
