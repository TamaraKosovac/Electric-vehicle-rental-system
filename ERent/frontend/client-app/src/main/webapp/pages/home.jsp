<%@ page import="org.unibl.etf.ip.erent.dto.ClientDTO" %>
<%
    ClientDTO client = (ClientDTO) session.getAttribute("client");
    if (client == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Home - eRent</title>
</head>
<body>
<h2>Welcome, <%= client.getFirstName() %> <%= client.getLastName() %>!</h2>

<ul>
    <li><a href="rent-car.jsp">Rent a Car</a></li>
    <li><a href="rent-bike.jsp">Rent a Bike</a></li>
    <li><a href="rent-scooter.jsp">Rent a Scooter</a></li>
    <li><a href="profile.jsp">Profile</a></li>
</ul>
</body>
</html>
