package com.mySSM.mySpringMVC;

import com.utils.StringUtil;
import com.mySSM.ioc.beanFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;


@WebServlet("*.do")
public class dispatcherServlet extends ViewBaseServlet {
    private beanFactory beanFactory = null;
    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext application = getServletContext();
        Object beanFactoryObj = application.getAttribute("beanFactory");
        if (beanFactoryObj != null) {
            beanFactory = (beanFactory) beanFactoryObj;
        } else {
            throw new RuntimeException("ioc error!");
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1);
        int lastIndexOf = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0,lastIndexOf);

        Object controllerBeanObj = beanFactory.getBean(servletPath);

        String key = req.getParameter("key");
        if (StringUtil.isEmpty(key)) {
            key = "index";
        }

        try {
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for (Method method :methods) {
                if (key.equals(method.getName())) {
                    Parameter[] parameters = method.getParameters();
                    Object[] parameterValues = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        String parameterName = parameter.getName();
                        if ("req".equals(parameterName)) {
                            parameterValues[i] = req;
                        } else if ("resp".equals(parameterName)) {
                            parameterValues[i] = resp;
                        } else if ("session".equals(parameterName)) {
                            parameterValues[i] = req.getSession();
                        } else {
                            String parameterValue = req.getParameter(parameterName);
                            Object parameterObj = parameterValue;

                            if (parameterObj != null) {
                                if ("java.lang.Integer".equals(parameter.getType().getName())) {
                                    parameterObj = Integer.parseInt(parameterValue);
                                }
                            }
                            parameterValues[i] = parameterObj;
                        }
                    }

                    method.setAccessible(true);
                    Object methodReturnObj = method.invoke(controllerBeanObj,parameterValues);
                    String methodReturnStr = (String) methodReturnObj;

                    if (methodReturnStr.startsWith("redirect:")) {
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        resp.sendRedirect(redirectStr);
                    } else {
                        super.processTemplate(methodReturnStr,req,resp);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DispatcherServletException("dispatcherServlet error!");
        }

//        Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
//        for (Method method :methods) {
//            String methodName = method.getName();
//            if (key.equals(methodName)) {
//                try {
//                    method.invoke(this,req,resp);
//                    return;
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//
//        throw new RuntimeException("invalid key!");
    }
}
