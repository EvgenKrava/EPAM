<%@include file="/WEB-INF/jspf/head.jspf"%>
<html>
<head>
    <title><fmt:message key="edit.user"/></title>
</head>
<body>
<header>
    <%@include file="/WEB-INF/jspf/header.jspf" %>
</header>
<main style=" width: 400px; height: 600px; margin:100px auto">
    <form method="post" action="controller?command=editUser">
        <table>
            <tr>
                <th><h3><fmt:message key="edit.user"/></h3></th>
            </tr>
            <tr>
                <td><input class="un" type="text" name="login" placeholder="<fmt:message key="login.login"/>" value="${euser.login}"></td>
            </tr>
            <tr>
                <td><input class="un" type="text" name="password" placeholder="<fmt:message key="password"/>" value="${euser.password}"></td>
            </tr>
            <tr>
                <td><input class="un" type="text" name="name" placeholder="<fmt:message key="name"/>" value="${euser.firstName}"></td>
            </tr>
            <tr>
                <td><input class="un" type="text" name="surname" placeholder="<fmt:message key="surname"/>" value="${euser.lastName}"></td>
            </tr>
            <tr>
                <td>
                    <select class="un" name="role">
                        <option value="ADMIN" ${euser.roleId == 1 ? 'selected' : ''}>ADMIN</option>
                        <option value="CLIENT" ${euser.roleId == 0 ? 'selected' : ''}>CLIENT</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><input class="submit" type="submit" value="<fmt:message key="edit"/>"></td>
            </tr>
        </table>
    </form>
</main>
</body>
</html>
