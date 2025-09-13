<%@ page import="org.unibl.etf.ip.erent.dto.ManagerDTO" %>
<%
    if ("logout".equals(request.getParameter("action"))) {
        session.invalidate();
        response.sendRedirect("login.jsp");
        return;
    }

    ManagerDTO manager = (ManagerDTO) session.getAttribute("manager");
    if (manager == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Home - Promotion App</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f4f4; }
        .container {
            width: 500px;
            margin: 50px auto;
            padding: 20px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 0 8px rgba(0,0,0,0.2);
        }
        h2 { color: #2e6f6a; }
        ul { list-style: none; padding: 0; }
        li { margin: 12px 0; }
        a { text-decoration: none; color: #2e6f6a; font-weight: bold; }
        a:hover { text-decoration: underline; }
        .logout { color: red; }
    </style>
</head>
<body>
<div class="container">
    <h2>Welcome, <%= manager.getFirstName() %> <%= manager.getLastName() %>!</h2>
    <p>Select an option below:</p>

    <ul>
        <li><a href="posts.jsp">📑 Manage Posts</a></li>
        <li><a href="promotions.jsp">🎉 Manage Promotions</a></li>
        <li>
            <a class="logout" href="home.jsp?action=logout"
               onclick="return confirm('Are you sure you want to logout?')">🚪 Logout</a>
        </li>
    </ul>
</div>
</body>
</html>
