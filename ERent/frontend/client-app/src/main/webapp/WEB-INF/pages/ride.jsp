<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    Long rentalId = (Long) session.getAttribute("rentalId");
    if (rentalId == null) {
        response.sendRedirect(request.getContextPath() + "/home");
        return;
    }

    Double pricePerHour = (Double) session.getAttribute("pricePerHour");
    if (pricePerHour == null) {
        pricePerHour = 0.0;
    }
    double basePrice = 0.0;
    LocalDateTime startTime = LocalDateTime.now();
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    String formattedStartTime = startTime.format(fmt);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ride in progress</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ride.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
<div class="ride-container">
    <h2><span class="material-icons">directions_car</span> Ride in progress...</h2>

    <p><strong>Start time:</strong> <%= formattedStartTime %></p>
    <p><strong>Elapsed time:</strong> <span id="timer">0:00</span></p>
    <p><strong>Estimated price:</strong> <span id="price">0.00</span> KM</p>

    <form id="endRideForm" action="${pageContext.request.contextPath}/rental" method="post" onsubmit="return validateEndRide()">
        <input type="hidden" name="action" value="end"/>
        <input type="hidden" name="latitude" id="endLat"/>
        <input type="hidden" name="longitude" id="endLon"/>
        <button type="submit" class="btn-end">
            <span class="material-icons">stop_circle</span>
            End ride
        </button>
    </form>
</div>

<script>
    let seconds = 0;
    let basePrice = <%= basePrice %>;
    let pricePerHour = <%= pricePerHour %>;
    let priceElement = document.getElementById("price");

    let timerInterval = setInterval(function () {
        seconds++;
        let minutes = Math.floor(seconds / 60);
        let sec = seconds % 60;
        document.getElementById("timer").textContent =
            minutes + ":" + (sec < 10 ? "0" + sec : sec);

        let price = basePrice + (seconds / 3600) * pricePerHour;
        priceElement.textContent = price.toFixed(2);
    }, 1000);

    function validateEndRide() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (pos) {
                document.getElementById("endLat").value = pos.coords.latitude;
                document.getElementById("endLon").value = pos.coords.longitude;
                clearInterval(timerInterval);
                document.getElementById("timer").textContent = "Finished";
                document.getElementById("endRideForm").submit();
            });
            return false;
        } else {
            alert("Geolocation not supported.");
            return false;
        }
    }
</script>
</body>
</html>