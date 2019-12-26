<%@include file="/WEB-INF/jspf/head.jspf" %>
<html>
<head>
    <title><fmt:message key="add.user"/></title>
    <meta charset="utf-8">

</head>
<body>

<%@include file="/WEB-INF/jspf/header.jspf" %>

<div class="content">
    <form method="post" action="controller?command=addUser">
        <div class="container">
            <h3><fmt:message key="add.user"/></h3>
            <input type="text" name="login" placeholder="<fmt:message key="login.login"/>">
            <input type="text" name="password" placeholder="<fmt:message key="password"/>">
            <input type="text" name="name" placeholder="<fmt:message key="name"/>">
            <input type="text" name="surname" placeholder="<fmt:message key="surname"/>">
            <select name="role">
                <option value="CLIENT">CLIENT</option>
                <option value="ADMIN">ADMIN</option>
            </select>
            <input type="submit" value="<fmt:message key="add"/>">
        </div>
    </form>
</div>
<footer>
    <jsp:include page="/WEB-INF/jspf/footer.jsp"/>
</footer>
</body>
</html>
