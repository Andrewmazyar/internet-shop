<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Orders</h2>
<table border="1">
    <tr>
        <th>Id</th>
        <th>User id</th>
        <th>User name</th>

    </tr>
    <c:forEach var="order" items="${orders}">
    <tr>
        <td>
            <c:out value="${order.orderId}"/>
        </td>
        <td>
            <c:out value="${order.user.id}"/>
        </td>
        <td>
            <c:out value="${order.user.userName}"/>
        </td>
    </tr>
    </c:forEach>
    <li><a href="/">main page</a></li>
</body>
</html>
