<%@ page import="org.unibl.etf.ip.erent.dto.ManagerDTO" %>
<%
    ManagerDTO manager = (ManagerDTO) session.getAttribute("manager");
    if (manager == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Home - Promotion App</title>
</head>
<body>
<h2>Welcome, <%= manager.getFirstName() %> <%= manager.getLastName() %>!</h2>

<ul>
    <li><a href="create-promotion.jsp">Create Promotion</a></li>
    <li><a href="create-post.jsp">Create Post</a></li>
    <li><a href="search.jsp">Search Promotions</a></li>
</ul>
</body>
</html>
