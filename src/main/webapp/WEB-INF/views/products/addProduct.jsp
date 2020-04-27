<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product</title>
</head>
<body>
<h3>Please add product</h3>

<form method="post" action="${pageContext.request.contextPath}/products/addProduct">
    Please enter name of product<input type="text" name="name"><br>
    Please enter price of product<input type="text" name="price"><br>
    <button type="submit">add product</button>
</form>
</body>
</html>
