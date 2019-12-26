<%@include file="/WEB-INF/jspf/head.jspf" %>
<html>
<head>
    <title><fmt:message key="users"/></title>
</head>
<body>
<%@include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
    <table class="table">
        <thead>
        <tr>
            <th colspan="8">
                <h2><fmt:message key="users"/></h2>
            </th>

        </tr>
        <tr>
            <th><fmt:message key="login.login"/></th>
            <th><fmt:message key="name"/></th>
            <th><fmt:message key="surname"/></th>
            <th><fmt:message key="role"/></th>
            <th><fmt:message key="tariffs"/></th>
            <th><fmt:message key="balance"/></th>
            <th colspan="2"><fmt:message key="action"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.login}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.role}</td>
                <td>
                    <ol>
                        <c:forEach items="${user.tariffs}" var="t">
                            <li>${t}</li>
                        </c:forEach>
                    </ol>
                </td>
                <td>${user.balance}</td>
                <th>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="editUserPage">
                        <input type="hidden" name="id" value="${user.id}">
                        <button type="submit"><i class="fas fa-user-edit"></i></button>
                    </form>
                </th>
                <th>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="deleteUser">
                        <input type="hidden" name="login" value="${user.login}">
                        <button type="submit"><i class="fas fa-user-minus" ></i></button>
                    </form>
                </th>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <h3><a href="controller?command=addUserPage"><i class="fas fa-user-plus" ></i><fmt:message
            key="add.user"/></a></h3>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
</body>
</html>
