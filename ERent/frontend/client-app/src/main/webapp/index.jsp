<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Redirecting...</title>
</head>
<body>
<%
    response.sendRedirect(request.getContextPath() + "/login");
%>
</body>
</html>
