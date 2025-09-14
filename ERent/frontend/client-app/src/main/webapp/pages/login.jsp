<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>eRent</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f4f4; }
        .login-container {
            width: 300px; margin: 100px auto; padding: 20px;
            background: #fff; border-radius: 8px; box-shadow: 0 0 8px rgba(0,0,0,0.2);
        }
        input { width: 100%; padding: 8px; margin: 8px 0; }
        button { width: 100%; padding: 10px; background: #2e6f6a; color: white; border: none; border-radius: 4px; }
        .error { color: red; font-size: 14px; }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Login</h2>
    <form action="${pageContext.request.contextPath}/login" method="post">
    <input type="text" name="username" placeholder="Username" required />
        <input type="password" name="password" placeholder="Password" required />
        <button type="submit">Login</button>
    </form>

    <div class="error">
        ${error}
    </div>
</div>
</body>
</html>
