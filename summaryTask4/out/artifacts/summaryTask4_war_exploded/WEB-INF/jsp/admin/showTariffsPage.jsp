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
                <th>${serice.name}</th>
            </c:forEach>
        </tr>
        </thead>
        <tbody>
        <tr>
            <c:forEach var="service" items="${services}">
                <th>
                    <table class="tariffs">
                        <thead>
                        <tr>
                            <th class="sort" onclick="sortTable(0, ${services.indexOf(service)})"><fmt:message
                                    key="name"/></th>
                            <th class="sort" onclick="sortTable(1, ${services.indexOf(service)})"><fmt:message
                                    key="price"/></th>
                            <th colspan="2"><fmt:message key="action"/></th>
                            <th>Users Count</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${service.tariffs}" var="tariff"><%--@elvariable id="usersCount" type="java.util.Map"--%>
                            <tr>
                                <td>${tariff.name}:</td>
                                <td>${tariff.price} грн</td>
                                <th>
                                    <form action="controller">
                                        <input type="hidden" name="command" value="editTariffPage">
                                        <input type="hidden" name="id" value="${tariff.id}">
                                        <button type="submit"><i class="fas fa-edit"></i></button>
                                    </form>
                                </th>
                                <th>
                                    <form action="controller">
                                        <input type="hidden" name="command" value="deleteTariff">
                                        <input type="hidden" name="id" value="${tariff.id}">
                                        <button type="submit"><i class="fas fa-trash-alt"></i></button>
                                    </form>
                                </th>
                                <th>${usersCount.get(tariff.id)}</th>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </th>
            </c:forEach>
        </tr>
        </tbody>
    </table>
    <h3><a href="controller?command=addTariffPage"><fmt:message key="add.tariff"/></a></h3>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jsp"/>
</body>
</html>
