<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of products</title>
</head>
<body>
<h2>Products</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>PRICE</th>
    </tr>
    <c:forEach var="product" items="${products}">
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
        <td>
            <a href="${pageContext.request.contextPath}/shoppingCartAdd?user_id=1&product_id=${product.id}">add</a>
        </td>
    </tr>
    </c:forEach>
    <li><a href="/">main page</a></li>
</body>
</html>
