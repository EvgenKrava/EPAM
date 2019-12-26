<%@include file="/WEB-INF/jspf/head.jspf" %>
<html>
<head>
    <title><fmt:message key="add.tariff"/></title>
    <meta charset="utf-8">
</head>
<body>
<header>
    <%@include file="/WEB-INF/jspf/header.jspf" %>
</header>
<div class="content">
    <form method="post" action="controller?command=addTariff">
        <div class="container">
            <h3><fmt:message key="add.tariff"/></h3>
            <input class="un" type="text" name="name" placeholder="<fmt:message key="name"/>">
            <input class="un" type="text" name="price" placeholder="<fmt:message key="price"/>">
            <select class="un" name="service_id">
                <c:forEach items="${services}" var="s">
                    <option value="${s.id}">${s.name}</option>
                </c:forEach>
            </select>
            <input class="submit" type="submit" value="<fmt:message key="add"/>">
        </div>
    </form>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
</body>
</html>
