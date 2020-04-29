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
        <th>User name</th>
        <th>Details</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="order" items="${orders}">
    <tr>
        <td>
            <c:out value="${order.id}"/>
        </td>
        <td>
            <c:out value="${order.user.id}"/>
        </td>
        <td>
            <c:out value="${order.user.name}"/>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/order/details?orderId=${order.id}">show</a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/deleteOrder?order_id=${order.id}">DELETE</a>
        </td>
    </tr>
    </c:forEach>
</body>
</html>
