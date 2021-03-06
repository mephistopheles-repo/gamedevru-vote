<%--
  Created by IntelliJ IDEA.
  User: denuss
  Date: 06.09.2014
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="taglibs.jsp" %>

<c:if test="${!isStubPage}">
    <script>
        (function () {
            setTimeout(function () {
                window.location.href = window.location.href;
            }, 1000 * 25);
        })();
    </script>

    <div class="overlay"
         style="position: absolute;width: 100%;height: 100%;opacity: 0.9;background-color: #FFFFFF;left: 0;top: 0;z-index: 4">

    </div>

    <div class="overlay"
         style="position: absolute;width: 100%;height: 100%;left: 0;top: 48; text-align: center; z-index: 5">
        Данные обновляются...
    </div>
</c:if>

<div class="vote-container">
    <span class="pull-right mrg-r-5">146%</span>

    <div class="vote" style="width: 60%">
        <span class="inner-text">Единая Россия</span>
    </div>
</div>

<div class="vote-container">
    <span class="pull-right mrg-r-5">146%</span>

    <div class="vote" style="width: 40%">
        <span class="inner-text">Единая Россия</span>
    </div>
</div>

<div class="vote-container">
    <span class="pull-right mrg-r-5">146%</span>

    <div class="vote" style="width: 20%">
        <span class="inner-text">Единая Россия</span>
    </div>
</div>

<div class="vote-container">
    <span class="pull-right mrg-r-5">146%</span>

    <div class="vote" style="width: 10%">
        <span class="inner-text">Единая Россия</span>
    </div>
</div>

<div class="vote-container">
    <span class="pull-right mrg-r-5">146%</span>

    <div class="vote" style="width: 5%">
        <span class="inner-text">Единая Россия</span>
    </div>
</div>
