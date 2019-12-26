<%@include file="/WEB-INF/jspf/head.jspf"%>
<html>
<head>
    <title><fmt:message key="error"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
</head>
<body>
<header>
    <%@include file="/WEB-INF/jspf/header.jspf" %>
</header>
<div style="padding-left:20px">
    <h3><fmt:message key="error"/></h3>
    <p>${errorMessage}</p>
    <a href="controller?command=home"><fmt:message key="home"/></a>
</div>
<footer>
    <jsp:include page="/WEB-INF/jspf/footer.jsp"/>
</footer>
</body>
</html>
