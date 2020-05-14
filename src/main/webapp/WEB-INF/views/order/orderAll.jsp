<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All orders</title>
</head>
<body>
<h2>Orders</h2>
<table border="1">
    <tr>
        <th>Id</th>
        <th>User id</th>
        <th>Details</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="order" items="${orders}">
    <tr>
        <td>
            <c:out value="${order.orderId}"/>
        </td>
        <td>
            <c:out value="${order.userId}"/>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/order/details?id=${order.orderId}">show</a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/deleteOrder?order_id=${order.orderId}">DELETE</a>
        </td>
    </tr>
    </c:forEach>
    <li><a href="/">main page</a></li>
</body>
</html>
