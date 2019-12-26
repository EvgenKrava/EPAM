<%@include file="/WEB-INF/jspf/head.jspf" %>
<html>
<head>
    <title><fmt:message key="home"/></title>
</head>
<body>
<%@include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
    <table class="user-info">
        <tr>
            <td><fmt:message key="login.login"/>:</td>
            <td>${user.login}</td>
        </tr>
        <tr>
            <td><fmt:message key="name"/>:</td>
            <td>${user.firstName}</td>
        </tr>
        <tr>
            <td><fmt:message key="surname"/>:</td>
            <td>${user.lastName}</td>
        </tr>
        <tr>
            <td><fmt:message key="role"/>:</td>
            <td>${user.role}</td>
        </tr>
    </table>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
</body>
</html>
