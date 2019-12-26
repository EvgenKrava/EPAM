<%@include file="WEB-INF/jspf/head.jspf" %>
<html>
<head>
    <title><fmt:message key="login"/></title>
    <meta charset="UTF-8">
</head>
<body>
<%@include file="WEB-INF/jspf/header.jspf" %>
<div class="content">
    <h2 class="title"><fmt:message key="login"/></h2>
    <form action="controller" method="post">
        <div class="container">
            <input type="hidden" name="command" value="login"/>
            <h3><fmt:message key="login.login"/></h3>
            <label>
                <input placeholder="<fmt:message key="login.login"/>" name="login">
            </label>
            <h3><fmt:message key="password"/></h3>
            <label>
                <input type="password" placeholder="<fmt:message key="password"/>" name="password">
            </label>
            <input class="submit" type="submit" value="<fmt:message key="login"/>"/>
        </div>
    </form>
</div>
<jsp:include page="WEB-INF/jspf/footer.jsp"/>
</body>
</html>