<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="ua.nure.kravchenko.summaryTask4.db.Role"
         pageEncoding="UTF-8"
%>

<html>
  <head>
    <title>Home</title>
  </head>
  <body>
  <jsp:include page="WEB-INF/jspf/header.jspf"/>
  <c:choose>
    <c:when test="${user == null}">
      <jsp:forward page="login.jsp"/>
    </c:when>
    <c:when test="${user.role == Role.ADMIN}">
      <jsp:forward page="WEB-INF/jsp/admin/adminPage.jsp"/>
    </c:when>
    <c:when test="${user.role == Role.CLIENT}">
      <jsp:forward page="WEB-INF/jsp/client/clientPage.jsp"/>
    </c:when>
  </c:choose>
  </body>
</html>
