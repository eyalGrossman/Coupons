<%@page import="org.w3c.dom.Attr"%>
<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.io.*"%>
<%@ page import="org.apache.log4j.*"%>
<%@page import="il.ac.telhai.coupons.controller.Attribute"%>
<!DOCTYPE html>
<html>

<head>
<title>Error!</title>
<meta name="viewport" content="width=device-width, initial-scale=1" http-equiv="Content-Type">
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css" />
<script type="text/javascript" src="/coupons/jquery-1.11.2.js"></script>
<script src="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>
</head>

<body>
    <div data-role="page">
        <div data-role="header">
            <a href="/coupons/index.html" data-icon="back">Back</a>
            <h1>Error</h1>
        </div>

        <div data-role="content">
            <%
                Exception e = (Exception) request.getAttribute(Attribute.ERROR.toString());
                Logger logger = (Logger) request.getAttribute(Attribute.LOGGER.toString());

                out.println("error has happend");
                if(e != null) {
                    out.println("<br>" + "message: " + e.getMessage());
                }
            %>

            <p>
                <%
                    if(e != null) {
                        logger.debug("STACK TRACE : ", e);

                        StringWriter stringWriter = new StringWriter();
                        PrintWriter printWriter = new PrintWriter(stringWriter);
                        e.printStackTrace(printWriter);
                        out.println(stringWriter);
                        printWriter.close();
                        stringWriter.close();
                    }
                %>
            </p>
        </div>

        <div data-role="footer">
            <h5>Nahum Farchi and Eyal Grossman, 2015, Telhai &copy</h5>
        </div>
    </div>

</body>

</html>