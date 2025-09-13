<%@ page import="org.unibl.etf.ip.erent.dao.PromotionDAO" %>
<%@ page import="org.unibl.etf.ip.erent.dto.PromotionDTO" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>

<%
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    if ("POST".equalsIgnoreCase(request.getMethod()) && request.getParameter("action") != null && request.getParameter("action").equals("create")) {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        PromotionDTO promo = new PromotionDTO(
                0,
                title,
                description,
                LocalDateTime.parse(startDate, formatter),
                LocalDateTime.parse(endDate, formatter),
                LocalDateTime.now()
        );

        PromotionDAO.insert(promo);
        response.sendRedirect("promotions.jsp");
        return;
    }

    String keyword = request.getParameter("q");
    List<PromotionDTO> promotions;
    if (keyword != null && !keyword.trim().isEmpty()) {
        promotions = PromotionDAO.search(keyword);
    } else {
        promotions = PromotionDAO.getAll();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Promotions</title>
</head>
<body>
<h2>Manage Promotions</h2>

<h3>Create New Promotion</h3>
<form method="post">
    <input type="hidden" name="action" value="create"/>
    Title: <input type="text" name="title" required><br><br>
    Description:<br>
    <textarea name="description" rows="4" cols="40" required></textarea><br><br>
    Start Date: <input type="datetime-local" name="startDate" required><br><br>
    End Date: <input type="datetime-local" name="endDate" required><br><br>
    <button type="submit">Save</button>
</form>

<hr>

<h3>Search Promotions</h3>
<form method="get">
    <input type="text" name="q" placeholder="Enter keyword" value="<%= keyword != null ? keyword : "" %>">
    <button type="submit">Search</button>
</form>

<hr>

<h3>All Promotions</h3>
<ul>
    <% for (PromotionDTO p : promotions) { %>
    <li>
        <b><%= p.getTitle() %></b><br>
        <%= p.getDescription() %><br>
        <small>
            From: <%= p.getStartDate() %> | To: <%= p.getEndDate() %><br>
            Created: <%= p.getCreatedAt() %>
        </small>
    </li>
    <% } %>
</ul>

</body>
</html>
