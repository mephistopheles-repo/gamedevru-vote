<%--
  Created by IntelliJ IDEA.
  User: denuss
  Date: 06.09.2014
  Time: 0:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="includes/taglibs.jsp" %>
<html>
<head>
    <title></title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body style="width: 100%;height: 100%">
<div class="main">
    <c:if test="${voting != null}">

    </c:if>
    <c:if test="${voting == null}">
        <%@include file="includes/loading-overlay.jsp" %>
    </c:if>
</div>

</body>
</html>
