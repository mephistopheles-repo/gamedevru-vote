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
            padding-right: 2px;
            padding-left: 2px;
            margin-right: 2px;
            margin-top: 2px;
            display: block;
            float: left;
            color: #2E2E5C;
            cursor: default;
        }

        .blue-pants.purple-pants {
            background: #E5D0D0;
        }

        .vote-container {
            width: 99%;
            height: 20px;
            background-color: <c:out value="${color.cssBack}"/>;
            margin: 1px 1px 1px 1px;
        }

        .voters-container {
            width: 100%;
            height: 103px;
            display: none;
            background-color: white;
        }

        .show-details .voters-container {
            display: block;
        }

        .show-details {
            width: 99%;
            height: 123px;
            overflow: auto;
            position: relative;
        }

        .vote {
            height: 20px;
            background-color: <c:out value="${color.cssComplete}"/>;
        }

        .main {
            width: 99%;
            height: 105px;
            margin-right: 1px;
            font-family: verdana, arial, sans-serif;
            font-size: 14px;
            position: absolute;
            top: 0;
            left: 0;
        }

        .inner-text {
            margin-left: 5px;
            color: <c:out value="${color.cssText}"/>;
            position: absolute;
        }

        .mrg-r-5 {
            margin-right: 5px;
        }

        .pull-right {
            float: right;
        }

        .vote-data {
            height: 125px;
            overflow: auto;
            width: 99%;
            position: relative;
            left: 0;
            top: 0;
        }
    </style>
    <script>
        function pressBackButton() {
            var el = document.getElementById("js-vote-data");
            el.style.display = "block";

            el = document.getElementById("js-main-menu");
            el.style.display = "block";

            el = document.getElementById("js-how-to-vote");
            el.style.display = "none";

            el = document.getElementById("js-how-to-make");
            el.style.display = "none";
        }

        function pressHowToVoteButton() {
            var el = document.getElementById("js-vote-data");
            el.style.display = "none";

            el = document.getElementById("js-main-menu");
            el.style.display = "none";

            el = document.getElementById("js-how-to-vote");
            el.style.display = "block";
        }

        function pressHowToMakeButton() {
            var el = document.getElementById("js-vote-data");
            el.style.display = "none";

            el = document.getElementById("js-main-menu");
            el.style.display = "none";

            el = document.getElementById("js-how-to-make");
            el.style.display = "block";
        }

        function pressVoteBar(el) {
            if (el.className.indexOf("show-details") != -1) {
                return;
            }
            var bars = document.getElementsByClassName("js-vote-bar");
            for (var i = 0; i < bars.length; i++) {
                bars.item(i).style.display = "none";
            }
            el.style.display = "block";
            el.className += " show-details";
        }

        function pressVotersContainer(el) {
            var parent = el.parentNode;
            while (parent.className.indexOf("show-details") != -1) {
                parent.className = parent.className.replace("show-details", "");
            }
            parent.className = parent.className.replace("  ", " ");
            var bars = document.getElementsByClassName("js-vote-bar");
            for (var i = 0; i < bars.length; i++) {
                bars.item(i).style.display = "block";
            }
            event.stopPropagation();
        }
    </script>
</head>
<body style="width: 100%;height: 100%">
<div class="main">
    <div id="js-vote-data" class="vote-data">
    <c:if test="${votingLength > 0}">
            <c:forEach items="${voting}" var="choise">
                <div class="vote-container js-vote-bar" onclick="pressVoteBar(this);">
                    <span class="pull-right mrg-r-5">${choise.count}</span>

                    <div class="vote" style="width: ${choise.count/(fullCount*0.01)}%">
                        <span class="inner-text"><c:out value="${choise.choice}"/></span>
                    </div>
                    <div class="voters-container" onclick="pressVotersContainer(this);">
                        <c:forEach items="${choise.voters}" var="voter">
                            <c:set var="purplePants" value=""/>
                            <c:if test="${voter.authorLevel == 'DONATOR' || voter.authorLevel == 'MODERATOR'}">
                                <c:set var="purplePants" value="purple-pants"/>
                            </c:if>
                            <span class="blue-pants ${purplePants}"
                                  title="${voter.authorLevel.textName}"><strong>${voter.authorName}</strong></span>
                        </c:forEach>
                    </div>
                </div>

            </c:forEach>
        </c:if>
        <c:if test="${votingLength == 0}">
            <%@include file="includes/loading-overlay.jsp" %>
        </c:if>
    </div>
    <div id="js-main-menu">
    <a href="javascript:void(0);" onclick="pressHowToVoteButton();">Как голосовать?</a>
        <a href="javascript:void(0);" onclick="pressHowToMakeButton();" class="pull-right mrg-r-5">Как создать?</a>
    </div>
    <%@include file="includes/help.jsp" %>
</div>

</body>
</html>
