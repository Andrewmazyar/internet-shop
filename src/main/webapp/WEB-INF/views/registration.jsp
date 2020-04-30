<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h3>Please enter valid form</h3>

<h4 style="color:red">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/registration">
    Please provide your name<input type="text" name="name"><br>
    Please provide your login<input type="text" name="login"><br>
    Please provide your password<input type="password" name="pwd"><br>
    Please repeat your password<input type="password" name="pwd-control"><br>
    <button type="submit">Register</button>
</form>
</body>
</html>
