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
    <title>Profile - eRent</title>
</head>
<body>
<h2>My Profile</h2>

<p><b>Username:</b> <%= client.getUsername() %></p>
<p><b>Name:</b> <%= client.getFirstName() %> <%= client.getLastName() %></p>
<p><b>Email:</b> <%= client.getEmail() %></p>

<form action="../changePassword" method="post">
    <h3>Change Password</h3>
    <input type="password" name="oldPassword" placeholder="Old Password" required />
    <input type="password" name="newPassword" placeholder="New Password" required />
    <button type="submit">Update</button>
</form>

<form action="../deactivate" method="post">
    <button type="submit" style="background:red; color:white;">Deactivate Account</button>
</form>

<p><a href="home.jsp">Back to Home</a></p>
</body>
</html>
