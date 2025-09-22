<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Rent a Scooter</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body>

<div class="page-container">

    <form action="${pageContext.request.contextPath}/rental" method="post"
          class="mt-3" onsubmit="return validateForm()">

        <input type="hidden" name="clientId" value="<%= client.getId() %>">
        <input type="hidden" name="vehicleType" value="SCOOTER">

        <div class="mb-3">
            <label class="form-label">Start location</label>
            <div class="d-flex gap-2">
                <input type="text" class="form-control" name="latitude" placeholder="Latitude"
                       required pattern="-?[0-9]{1,2}(\.[0-9]+)?"
                       title="Please enter a valid latitude (e.g. 44.778)">
                <input type="text" class="form-control" name="longitude" placeholder="Longitude"
                       required pattern="-?(180(\.0+)?|((1[0-7][0-9])|([0-9]{1,2}))(\.[0-9]+)?)"
                       title="Please enter a valid longitude (e.g. 17.191)">
            </div>
            <div class="d-flex justify-content-end mt-2">
                <button type="button" class="btn-outline-green" onclick="detectLocation()">
                    <span class="material-icons" style="font-size:18px; vertical-align:middle; margin-right:6px;">
                        my_location
                    </span>
                    Detect automatically
                </button>
            </div>
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

        <div class="mb-3">
            <label class="form-label">Card details</label>
            <input type="text" class="form-control" name="cardNumber"
                   placeholder="1234567890123456" required
                   pattern="^[0-9]{16}$" title="Card number must contain exactly 16 digits (no spaces)">
        </div>
        <div class="d-flex gap-2 mb-3">
            <input type="text" class="form-control" name="expiryDate"
                   placeholder="MM/YY" required
                   pattern="^(0[1-9]|1[0-2])/\d{2}$"
                   title="Expiry date must be in format MM/YY">
            <input type="text" class="form-control" name="cvv"
                   placeholder="CVV" required
                   pattern="^[0-9]{3,4}$" title="CVV must be 3 or 4 digits">
        </div>

        <div class="mb-3">
            <label class="form-label">ID document(passport/ID card)</label>
            <input type="text" class="form-control" name="idDocument"
                   placeholder="e.g. A1234567" required
                   pattern="[A-Za-z0-9]{5,20}"
                   title="Enter valid passport/ID number">
        </div>
        <div class="mb-3">
            <label class="form-label">Driver's license number</label>
            <input type="text" class="form-control" name="licenseNumber"
                   placeholder="e.g. B1234567" required
                   pattern="[A-Za-z0-9]{5,20}"
                   title="Enter valid driver's license number">
        </div>

        <div class="d-flex justify-content-end">
            <button type="submit" class="btn-green">
                <span class="material-icons" style="font-size:18px; vertical-align:middle; margin-right:6px;">
                    play_circle_filled
                </span>
                Start ride
            </button>
        </div>
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
            alert("Geolocation is not supported in your browser.");
        }
    }

    function validateForm() {
        const form = document.forms[0];
        if (!form.checkValidity()) {
            alert("Please fill in all fields correctly before submitting.");
            return false;
        }
        return true;
    }
</script>

</body>
</html>