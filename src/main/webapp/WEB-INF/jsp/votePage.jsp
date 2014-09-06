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
    <style>
        .overlays {
            display: none;
        }

        .vote-container {
            width: 100%;
            height: 20px;
            background-color: rgb(213, 213, 213);
            margin: 1px 1px 1px 1px;
        }

        .vote {
            height: 100%;
            background-color: rgb(35, 151, 35);;
        }

        .main {
            width: 99%;
            height: 105px;
            margin-right: 1px;
        }

        .inner-text {
            margin-left: 5px;
            color: ghostwhite;
            position: absolute;
        }

        .mrg-r-5 {
            margin-right: 5px;
        }

        .pull-right {
            float: right;
        }
    </style>
</head>
<body style="width: 100%;height: 100%">
<div class="main">
    <c:if test="${votingLength > 0}">
        <c:forEach items="${voting}" var="choise">
            <div class="vote-container">
                <span class="pull-right mrg-r-5">${choise.count}</span>

                <div class="vote" style="width: ${choise.count/(fullCount*0.01)}%">
                    <span class="inner-text"><c:out value="${choise.choice}"/></span>
                </div>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${votingLength == 0}">
        <%@include file="includes/loading-overlay.jsp" %>
    </c:if>
</div>

</body>
</html>
