package com.javaclasses.todolistapp.controllers;

import com.javaclasses.todolistapp.HandlerProcessingResult;

import javax.servlet.http.HttpServletRequest;

/**
 * public API of request handler
 */
public interface Handler {

    HandlerProcessingResult processRequest(HttpServletRequest request);
}
