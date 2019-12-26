<%@include file="/WEB-INF/jspf/head.jspf"%>
<html>
<head>
    <title><fmt:message key="edit"/> <fmt:message key="tariff"/></title>
</head>
<body>
<header>
    <%@include file="/WEB-INF/jspf/header.jspf" %>
</header>
<main style=" width: 400px; height: 600px; margin:100px auto">
    <form method="post" action="controller?command=editTariff">
        <table>
            <tr>
                <th><h3><fmt:message key="edit"/> <fmt:message key="tariff"/></h3></th>
            </tr>
            <tr>
                <td><input class="un" type="text" name="name" placeholder="<fmt:message key="name"/>" value="${tariff.name}"></td>
            </tr>
            <tr>
                <td><input class="un" type="text" name="price" placeholder="<fmt:message key="price"/>" value="${tariff.price}"></td>
            </tr>
            <tr>
                <td>
                    <select class="un" name="service_id">
                        <c:forEach items="${services}" var="s">
                            <option value="${s.id}" ${s.id == tariff.serviceId ? 'selected' : ''}>${s.name}</option>
                        </c:forEach>
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
