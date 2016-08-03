package com.javaclasses.todolistapp;


import com.javaclasses.todolistapp.controllers.Handler;
import com.javaclasses.todolistapp.controllers.UserController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {

    static {
        UserController.getInstance();
    }

    private final HandlerRegistry handlerRegistry = HandlerRegistry.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);

    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final String uri = request.getRequestURI();
        final String method = request.getMethod();

        final CompoundKey key = new CompoundKey(uri, method);

        final Handler handler = handlerRegistry.getHandler(key);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            HandlerProcessingResult handlerProcessingResult = handler.processRequest(request);
            response.setStatus(handlerProcessingResult.getResponseStatus());
            response.getWriter().write(handlerProcessingResult.getContent());
        }
    }
}
