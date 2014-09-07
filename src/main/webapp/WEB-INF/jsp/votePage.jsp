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
        .hide {
            display: none;
        }

        .blue-pants {
            background: #D5E0E0;
        }

        .purple-pants {
            background: #E5D0D0;
        }

        .vote-container {
            width: 100%;
            height: 20px;
            background-color: #<c:out value="${color.back}"/>;
            margin: 1px 1px 1px 1px;
        }

        .vote {
            height: 100%;
            background-color: #<c:out value="${color.complete}"/>;
        }

        .main {
            width: 99%;
            height: 105px;
            margin-right: 1px;
        }

        .inner-text {
            margin-left: 5px;
            color:  #<c:out value="${color.text}"/>;
            position: absolute;
        }

        .mrg-r-5 {
            margin-right: 5px;
        }

        .pull-right {
            float: right;
        }
    </style>
    <script>
        function pressBackButton(){
            var el = document.getElementById("js-vote-data");
            el.style.display = "block";

            el = document.getElementById("js-how-to-vote");
            el.style.display = "none";

            el = document.getElementById("js-how-to-make");
            el.style.display = "none";
        }

        function pressHowToVoteButton(){
            var el = document.getElementById("js-vote-data");
            el.style.display = "none";

            el = document.getElementById("js-how-to-vote");
            el.style.display = "block";
        }
    </script>
</head>
<body style="width: 100%;height: 100%">
<div class="main">
    <div id="js-vote-data">
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
        <a href="javascript:void(0);" onclick="pressHowToVoteButton();" target="_blank">Как голосовать?</a>
        <a href="javascript:void(0);" class="pull-right mrg-r-5" target="_blank">Как создать?</a>
    </div>
    <div id="js-how-to-vote" style="display: none">
        <span>
            Для голосования достаточно в своем сообщении на фореме написать:
            </br>
                @vote <strong>ваш_вариант_ответа</strong>
            </br>
                где <strong>ваш_вариант_ответа</strong> это ваш вариант ответа.
        </span>
        <a href="javascript:void(0);" onclick="pressBackButton();" style="position: relative;bottom: 0;left: 0;"> Назад</a>
    </div>

    <div id="js-how-to-make" style="display: none">
        <span>
            <pre>Тут будет текст
            </pre>
        </span>
        <a href="javascript:void(0);" onclick="pressBackButton();" style="position: relative;bottom: 0;left: 0;"> Назад</a>
    </div>
</div>

</body>
</html>
