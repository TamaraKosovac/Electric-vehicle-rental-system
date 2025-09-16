<%@ page import="org.unibl.etf.ip.erent.dto.ClientDTO" %>
<%
    ClientDTO client = (ClientDTO) session.getAttribute("client");
    if (client == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>