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

    String f = client.getFirstName() != null ? client.getFirstName() : "";
    String l = client.getLastName() != null ? client.getLastName() : "";
    String initials = (f.isEmpty() ? "" : String.valueOf(f.charAt(0))) + (l.isEmpty() ? "" : String.valueOf(l.charAt(0)));
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

    <div class="card profile-card shadow-sm mb-4">
        <div class="card-body py-3 px-4">
            <div class="row g-3 align-items-center">
                <div class="col-auto">
                    <div class="pc-avatar pc-avatar-lg">
                        <img
                                class="pc-avatar-img"
                                src="${pageContext.request.contextPath}/clientphotocontroller?clientId=<%= client.getId() %>"
                                alt="Profile photo"
                                onerror="this.remove(); this.parentElement.classList.add('pc-avatar-fallback'); this.parentElement.innerHTML='<span><%= initials.toUpperCase() %></span>';"
                        />
                    </div>
                </div>

                <div class="col">
                    <h5 class="mb-2">
                        <%= client.getFirstName() %> <%= client.getLastName() %>
                    </h5>
                    <div class="row row-cols-1 row-cols-sm-2 gy-2 gx-4">
                        <div class="col">
                            <span class="pc-label">Username</span>
                            <span class="pc-value"><%= client.getUsername() %></span>
                        </div>
                        <div class="col">
                            <span class="pc-label">Email</span>
                            <span class="pc-value"><%= client.getEmail() %></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


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
                <th>Duration (h)</th>
                <th>Price (KM)</th>
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