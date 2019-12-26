<%@include file="/WEB-INF/jspf/head.jspf" %>
<html>
<head>
    <title><fmt:message key="services"/></title>
    <script src="${pageContext.request.contextPath}/js/sort.js"></script>
</head>
<body>
<%@include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
    <table class="main-table">
        <thead>
        <tr>
            <th colspan="${services.size()}">
                <h2><fmt:message key="services"/></h2>
            </th>
        </tr>
        <tr>
            <c:forEach items="${services}" var="serice">
                <th><b>${serice.name}</b></th>
            </c:forEach>
        </tr>
        </thead>
        <tbody>
        <tr>
            <c:forEach var="service" items="${services}">
                <td>
                    <table class="tariffs" width="100%">
                        <thead>
                        <tr>
                            <th class="sort" onclick="sortTable(0, ${services.indexOf(service)})"><fmt:message
                                    key="name"/></th>
                            <th class="sort" onclick="sortTable(1, ${services.indexOf(service)})"><fmt:message
                                    key="price"/></th>
                            <th><fmt:message key="action"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${service.tariffs}" var="tariff">
                            <tr>
                                <td>${tariff.name}:</td>
                                <td>${tariff.price} грн</td>
                                <c:if test="${user.tariffs.contains(tariff)}">
                                    <th>
                                        <form action="controller">
                                            <button type="submit"><i class="fas fa-minus" style="font-size: 20px"></i></button>
                                            <input type="hidden" name="command" value="denyTariff">
                                            <input type="hidden" name="tariffId" value="${tariff.id}">
                                        </form>
                                    </th>
                                </c:if>
                                <c:if test="${!user.tariffs.contains(tariff)}">
                                    <th>
                                        <form action="controller">
                                            <input type="hidden" name="command" value="includeTariff">
                                            <button type="submit"><i class="fas fa-plus" style="font-size: 20px"></i></button>
                                            <input type="hidden" name="tariffId" value="${tariff.id}">
                                        </form>
                                    </th>
                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>
            </c:forEach>
        </tr>
        </tbody>
    </table>
    <h3>
        <a href="<c:url value="/controller?command=downloadServices"/>" download>
            <fmt:message key="download.services.data"/>
        </a>
    </h3>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
</body>
</html>
