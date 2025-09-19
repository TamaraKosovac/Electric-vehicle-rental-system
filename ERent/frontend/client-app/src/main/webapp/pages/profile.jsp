<%@ page import="org.unibl.etf.ip.erent.dto.ClientDTO" %>
<%@ page import="org.unibl.etf.ip.erent.dto.RentalDTO" %>
<%@ page import="org.unibl.etf.ip.erent.dao.RentalDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    ClientDTO client = (ClientDTO) session.getAttribute("client");
    if (client == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<RentalDTO> rentals = RentalDAO.getRentalsByClient(client.getId());
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body class="bg-light">

<div class="container my-4">

    <h2 class="mb-3 text-primary">My Profile</h2>

    <p><b>Username:</b> <%= client.getUsername() %></p>
    <p><b>Name:</b> <%= client.getFirstName() %> <%= client.getLastName() %></p>
    <p><b>Email:</b> <%= client.getEmail() %></p>

    <h3 class="mt-4">Change Password</h3>
    <form action="${pageContext.request.contextPath}/profile" method="post"
          class="p-3 border rounded bg-white shadow-sm mb-4">
        <input type="hidden" name="action" value="changePassword">
        <input type="hidden" name="clientId" value="<%= client.getId() %>">

        <div class="mb-3">
            <label class="form-label">Old Password:</label>
            <input type="password" name="oldPassword" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">New Password:</label>
            <input type="password" name="newPassword" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Confirm New Password:</label>
            <input type="password" name="confirmPassword" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-primary">Change Password</button>
    </form>

    <form action="${pageContext.request.contextPath}/profile" method="post"
          onsubmit="return confirm('Are you sure you want to deactivate your profile?');"
          class="p-3 border rounded bg-white shadow-sm mb-4">
        <input type="hidden" name="action" value="deactivate">
        <input type="hidden" name="clientId" value="<%= client.getId() %>">
        <button type="submit" class="btn btn-danger">Deactivate Profile</button>
    </form>

    <h3 class="mt-4">My Rentals (found: <%= rentals.size() %>)</h3>
    <% if (rentals.isEmpty()) { %>
    <p>No rentals found.</p>
    <% } else { %>
    <div class="table-responsive">
        <table class="styled-table">
            <thead>
            <tr>
                <th>Manufacturer</th>
                <th>Model</th>
                <th>Start time</th>
                <th>End time</th>
                <th>Duration(h)</th>
                <th>Price(KM)</th>
                <th>Start latitude</th>
                <th>Start longitude</th>
                <th>End latitude</th>
                <th>End longitude</th>
            </tr>
            </thead>
            <tbody>
            <% for (RentalDTO r : rentals) { %>
            <tr>
                <td><%= r.getManufacturer() %></td>
                <td><%= r.getModel() %></td>
                <td><%= r.getStartDateTime().format(fmt) %></td>
                <td><%= r.getEndDateTime() != null ? r.getEndDateTime().format(fmt) : "-" %></td>
                <td><%= r.getDuration() %></td>
                <td><%= r.getPrice() %></td>
                <td><%= r.getStartLatitude() %></td>
                <td><%= r.getStartLongitude() %></td>
                <td><%= r.getEndLatitude() %></td>
                <td><%= r.getEndLongitude() %></td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
    <% } %>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>