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

<h2>My Profile</h2>

<p><b>Username:</b> <%= client.getUsername() %></p>
<p><b>Name:</b> <%= client.getFirstName() %> <%= client.getLastName() %></p>
<p><b>Email:</b> <%= client.getEmail() %></p>

<h3>My Rentals (found: <%= rentals.size() %>)</h3>
<% if (rentals.isEmpty()) { %>
<p>No rentals found.</p>
<% } else { %>
<table>
    <tr>
        <th>Manufacturer</th>
        <th>Model</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Duration (h)</th>
        <th>Price (KM)</th>
        <th>Start Lat</th>
        <th>Start Lng</th>
        <th>End Lat</th>
        <th>End Lng</th>
    </tr>
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
</table>
<% } %>