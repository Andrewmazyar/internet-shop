<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Internet Shop</title>
</head>
<body>
<h1>Bear Beer Shop</h1>
<a href="${pageContext.request.contextPath}/logout">Logout</a>

<h4>Order</h4>
<ul>
    <li><a href="/order/orderAll">all orders</a></li>
    <li><a href="/order/allOrders">all orders for admin</a></li>
    <li><a href="/order/details">detail for order</a></li>
</ul>
<h4>Product</h4>
<ul>
    <li><a href="/products/addProduct">Add Product</a></li>
    <li><a href="/products/listProduct">list Product for User</a></li>
    <li><a href="/products/listProductAdmin">list Product for Admin</a></li>
</ul>
<h4>shopping cart</h4>
<ul>
    <li><a href="/shoppingCart/shoppingCart">Shopping Cart</a></li>
</ul>
<h4>User</h4>
<ul>
    <li><a href="/registration">Registration</a></li>
    <li><a href="/users/listUsers">List Of Users</a></li>
    <li><a href="/login">login</a></li>
</ul>
</body>
<footer>
    <p>Hello!!! Do you want to dance)))) <a href="https://www.youtube.com/watch?v=fVGCLEAqUtA">click dance</a> ?</p>
    <h2> today: ${time}</h2>
</footer>
</html>
