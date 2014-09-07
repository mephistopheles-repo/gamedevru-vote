<%--
  Created by IntelliJ IDEA.
  User: denuss
  Date: 06.09.2014
  Time: 0:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
<body>
<script>
    (function () {
        var parentHref = (window.location != window.parent.location) ? document.referrer : document.location;
        var params = location.search;

        if (!params.length) {
            params = "?";
        } else {
            params = params + "&";
        }

        window.location.href = "/vote-page" + params + "url=" + encodeURIComponent(parentHref);
    })
    ()
</script>

</body>
</html>
