<%@include file="/WEB-INF/jspf/head.jspf" %>
<html>
<head>
    <title><fmt:message key="top.up"/></title>
</head>
<body>
<%@include file="../../jspf/header.jspf" %>
<div class="content">
    <form action="controller" method="get">
        <div class="container">
            <input type="hidden" name="command" value="addMoney">
            <h2><fmt:message key="refill"/></h2>
            <input class="un" name="login" placeholder="<fmt:message key="login.login"/>">
            <input class="un" name="amount" placeholder="<fmt:message key="amount"/>">
            <input class="submit" type="submit" value="<fmt:message key="submit"/>">
        </div>
    </form>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
</body>
</html>
