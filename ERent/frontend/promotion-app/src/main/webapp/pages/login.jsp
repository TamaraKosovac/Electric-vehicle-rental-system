<%@ page import="org.unibl.etf.ip.erent.dao.ManagerDAO" %>
<%@ page import="org.unibl.etf.ip.erent.dto.ManagerDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = null;
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ManagerDTO manager = ManagerDAO.login(username, password);

        if (manager != null) {
            session.setAttribute("manager", manager);
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
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
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