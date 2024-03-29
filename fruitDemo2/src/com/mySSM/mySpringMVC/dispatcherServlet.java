package com.mySSM.mySpringMVC;

import com.mySSM.mySpringMVC.ViewBaseServlet;
import com.utils.stringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;


@WebServlet("*.do")
public class dispatcherServlet extends ViewBaseServlet {
    private Map<String, Object> beanMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(resourceAsStream);
            NodeList beanList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanList.getLength(); i++) {
                Node beanNode = beanList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    String id = beanElement.getAttribute("id");
                    String aClass = beanElement.getAttribute("class");
                    Class controllerBeanObj = Class.forName(aClass);
                    Object beanObj = controllerBeanObj.newInstance();

                    beanMap.put(id,beanObj);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1);
        int lastIndexOf = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0,lastIndexOf);

        Object controllerBeanObj = beanMap.get(servletPath);

        String key = req.getParameter("key");
        if (stringUtil.isEmpty(key)) {
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
            throw new RuntimeException();
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
