<%@ page import="org.unibl.etf.ip.erent.dao.PostDAO" %>
<%@ page import="org.unibl.etf.ip.erent.dto.PostDTO" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.List" %>

<%
    if ("POST".equalsIgnoreCase(request.getMethod()) && request.getParameter("action") != null && request.getParameter("action").equals("create")) {
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        PostDTO post = new PostDTO(0, title, content, LocalDateTime.now());
        PostDAO.insert(post);
        response.sendRedirect("posts.jsp");
        return;
    }

    String keyword = request.getParameter("q");
    List<PostDTO> posts;
    if (keyword != null && !keyword.trim().isEmpty()) {
        posts = PostDAO.search(keyword);
    } else {
        posts = PostDAO.getAll();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Posts</title>
</head>
<body>
<h2>Manage Posts</h2>

<h3>Create New Post</h3>
<form method="post">
    <input type="hidden" name="action" value="create"/>
    Title: <input type="text" name="title" required><br><br>
    Content:<br>
    <textarea name="content" rows="4" cols="40" required></textarea><br><br>
    <button type="submit">Save</button>
</form>

<hr>

<!-- Forma za pretragu -->
<h3>Search Posts</h3>
<form method="get">
    <input type="text" name="q" placeholder="Enter keyword" value="<%= keyword != null ? keyword : "" %>">
    <button type="submit">Search</button>
</form>

<hr>

<h3>All Posts</h3>
<ul>
    <% for (PostDTO p : posts) { %>
    <li>
        <b><%= p.getTitle() %></b><br>
        <%= p.getContent() %><br>
        <small><%= p.getCreatedAt() %></small>
    </li>
    <% } %>
</ul>

</body>
</html>
