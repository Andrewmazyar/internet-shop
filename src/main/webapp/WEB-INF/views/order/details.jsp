<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Order Detail</title>
</head>
<body>
<h2>Orders detail</h2>
<table border="1">
    <tr>
        <th>Id</th>
        <th>name</th>
        <th>Price</th>
    </tr>
    <c:forEach var="product" items="${order.products}">
    <tr>
        <td>
            <c:out value="${product.id}"/>
        </td>
        <td>
            <c:out value="${product.name}"/>
        </td>
        <td>
            <c:out value="${product.price}"/>
        </td>
    </tr>
    </c:forEach>
    <a href="${pageContext.request.contextPath}/deleteOrder?order_id=${order.id}">DELETE</a>
    <li><a href="/">main page</a></li>
</body>
</html>
