<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>login page</h1>

<h4 style="color:red">${errorMessage}</h4>

<form action="${pageContext.request.contextPath}/login" method="post">
    Please provide your login<input type="text" name="login">
    Please provide your password<input type="password" name="pwd"><br>
    <button type="submit">Login</button>
    <li><a href="/registration">Registration</a></li>
</form>
</body>
</html>
