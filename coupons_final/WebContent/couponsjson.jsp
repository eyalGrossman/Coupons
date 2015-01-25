<%@ page language="java" contentType="application/json" pageEncoding="UTF-8"%>
<%@ page import="org.apache.log4j.*"%>
<%@ page import="org.json.JSONArray"%>
<%@ page import="org.json.JSONObject"%>
<%@page import="il.ac.telhai.coupons.controller.Attribute"%>
<%@page import="il.ac.telhai.coupons.model.CouponException"%>

<%
    Logger logger = (Logger) request.getAttribute(Attribute.LOGGER.toString());

    // Get the coupons JSONArray and print it out.
    logger.debug("Getting coupons JSONArray...\n");
    JSONObject couponsJSON = (JSONObject) request
            .getAttribute(Attribute.COUPONS_JSON.toString());
    logger.debug("Got: " + couponsJSON + "\n");
    out.print(couponsJSON);
    out.flush();
%>
