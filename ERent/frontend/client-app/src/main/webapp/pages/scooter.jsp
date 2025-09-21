<%@ page import="org.unibl.etf.ip.erent.dto.ClientDTO" %>
<%@ page import="org.unibl.etf.ip.erent.dto.ScooterDTO" %>
<%@ page import="org.unibl.etf.ip.erent.dao.ScooterDAO" %>
<%@ page import="org.unibl.etf.ip.erent.dao.RentalPriceDAO" %>
<%@ page import="org.unibl.etf.ip.erent.dto.RentalPriceDTO" %>
<%@ page import="java.util.List" %>

<%
    ClientDTO client = (ClientDTO) session.getAttribute("client");
    if (client == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<ScooterDTO> scooters = ScooterDAO.findAvailableDTO();
    RentalPriceDTO priceScooter = RentalPriceDAO.getByVehicleType("SCOOTER");
%>

<div class="page-container">

    <form action="${pageContext.request.contextPath}/rent" method="post" class="mt-3">

        <input type="hidden" name="clientId" value="<%= client.getId() %>">
        <input type="hidden" name="vehicleType" value="SCOOTER">

        <div class="mb-3">
            <label class="form-label">Start location (latitude, longitude)</label>
            <div class="d-flex gap-2">
                <input type="text" class="form-control" name="latitude" placeholder="Latitude" required>
                <input type="text" class="form-control" name="longitude" placeholder="Longitude" required>
            </div>
            <button type="button" class="btn btn-sm btn-outline-secondary mt-1" onclick="detectLocation()">Detect automatically</button>
        </div>

        <div class="mb-3">
            <label class="form-label">Choose a scooter</label>
            <select name="vehicleId" class="form-select" required>
                <% for (ScooterDTO scooter : scooters) { %>
                <option value="<%= scooter.getId() %>">
                    <%= scooter.getManufacturerName() %> - <%= scooter.getModel() %>
                    (<%= priceScooter != null ? priceScooter.getPricePerHour() : "N/A" %> KM/h)
                </option>
                <% } %>
            </select>
        </div>

        <h5>Payment info</h5>
        <div class="mb-3">
            <label class="form-label">Card number</label>
            <input type="text" class="form-control" name="cardNumber" placeholder="1234 5678 9012 3456" required>
        </div>
        <div class="d-flex gap-2 mb-3">
            <input type="text" class="form-control" name="expiryDate" placeholder="MM/YY" required>
            <input type="text" class="form-control" name="cvv" placeholder="CVV" required>
        </div>

        <button type="submit" class="btn btn-success">Start ride</button>
    </form>
</div>

<script>
    function detectLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                document.querySelector('[name="latitude"]').value = position.coords.latitude;
                document.querySelector('[name="longitude"]').value = position.coords.longitude;
            }, function(error) {
                alert("Cannot detect location: " + error.message);
            });
        } else {
            alert("Geolocation not supported in your browser.");
        }
    }
</script>