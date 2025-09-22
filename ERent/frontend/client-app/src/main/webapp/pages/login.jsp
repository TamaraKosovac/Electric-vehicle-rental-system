<%@ page import="org.unibl.etf.ip.erent.dao.ClientDAO" %>
<%@ page import="org.unibl.etf.ip.erent.dto.ClientDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = null;
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ClientDTO client = ClientDAO.login(username, password);

        if (client != null) {
            session.setAttribute("client", client);
            response.sendRedirect(request.getContextPath() + "/pages/home.jsp");
            return;
        } else {
            error = "Invalid username or password!";
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>eRent</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css?v=2">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body class="login-body">

<div class="login-container">
    <div class="login-box">
        <div class="login-header">
            <div class="brand">
                <img src="${pageContext.request.contextPath}/images/logo.png" alt="Logo" class="login-logo" />
                <h2 class="login-title">eRent</h2>
                <div class="login-tagline">Easy. Electric. Everywhere.</div>
            </div>
        </div>

        <form method="post" class="login-form">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" name="username" placeholder="Enter username" required class="full-width"/>
            </div>

            <div class="form-group password-group">
                <label for="password">Password</label>
                <div class="password-wrapper">
                    <input type="password" id="password" name="password" placeholder="Enter password" required class="full-width"/>
                    <button type="button" class="toggle-password" onclick="togglePassword()">
                        <span id="eye-icon" class="material-icons">visibility_off</span>
                    </button>
                </div>
            </div>

            <button type="submit" class="btn-login">Login</button>
            <div class="register-link">
                Don’t have an account?
                <a href="${pageContext.request.contextPath}/pages/register.jsp">Register</a>
            </div>
        </form>

        <p class="error-msg"><%= error != null ? error : "" %></p>
    </div>
</div>
<script>
    function togglePassword() {
        const passwordField = document.getElementById("password");
        const eyeIcon = document.getElementById("eye-icon");

        if (passwordField.type === "password") {
            passwordField.type = "text";
            eyeIcon.textContent = "visibility";
        } else {
            passwordField.type = "password";
            eyeIcon.textContent = "visibility_off";
        }
    }
</script>
</body>
</html>