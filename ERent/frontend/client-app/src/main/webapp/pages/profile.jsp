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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body class="bg-light">

<div class="container my-4">
    <div class="client-card shadow-sm mb-4">
        <div class="client-image">
            <img
                    src="${pageContext.request.contextPath}/clientphotocontroller?clientId=<%= client.getId() %>"
                    alt="Profile photo"
                    onerror="this.remove(); this.parentElement.classList.add('client-avatar-fallback'); this.parentElement.innerHTML='<span><%= initials.toUpperCase() %></span>';"
            />
        </div>

        <div class="client-info">
            <div class="info-grid">
                <div><i class="bi bi-person"></i><%= client.getUsername() %></div>
                <div><i class="bi bi-person"></i><%= client.getFirstName() %> <%= client.getLastName() %></div>
                <div><i class="bi bi-envelope"></i><%= client.getEmail() %></div>
                <div><i class="bi bi-telephone"></i><%= client.getPhone() %></div>
                <div><i class="bi bi-card-text"></i><%= client.getDocumentType() %> - <%= client.getDocumentNumber() %></div>
                <div><i class="bi bi-credit-card-2-front"></i><%= client.getDrivingLicense() %></div>
            </div>
        </div>
    </div>

    <div class="d-flex justify-content-end gap-3 mb-3">
        <button class="btn btn-green" data-bs-toggle="modal" data-bs-target="#changePasswordModal">
            <i class="bi bi-key"></i> Change password
        </button>

        <button class="btn btn-outline-green" data-bs-toggle="modal" data-bs-target="#deactivateModal">
            <i class="bi bi-person-x"></i> Deactivate profile
        </button>
    </div>

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

<div class="modal fade" id="changePasswordModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content dlg">
            <div class="dlg-header">
                <h2>Change password</h2>
            </div>

            <form action="${pageContext.request.contextPath}/profile" method="post">
                <div class="dlg-content text-start">
                    <input type="hidden" name="action" value="changePassword">
                    <input type="hidden" name="clientId" value="<%= client.getId() %>">

                    <div class="row g-3 mb-3">
                        <div class="col">
                            <label class="form-label">Old password</label>
                            <div class="password-wrapper">
                                <input type="password" id="oldPassword" name="oldPassword"
                                       class="form-control" placeholder="Old password" required>
                                <button type="button" class="toggle-password" onclick="togglePassword('oldPassword', 'eye-old')">
                                    <span id="eye-old" class="material-icons">visibility_off</span>
                                </button>
                            </div>
                        </div>
                        <div class="col">
                            <label class="form-label">New password</label>
                            <div class="password-wrapper">
                                <input type="password" id="newPassword" name="newPassword"
                                       class="form-control" placeholder="New password" required>
                                <button type="button" class="toggle-password" onclick="togglePassword('newPassword', 'eye-new')">
                                    <span id="eye-new" class="material-icons">visibility_off</span>
                                </button>
                            </div>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Confirm new password</label>
                        <div class="password-wrapper">
                            <input type="password" id="confirmPassword" name="confirmPassword"
                                   class="form-control" placeholder="Confirm new password" required>
                            <button type="button" class="toggle-password" onclick="togglePassword('confirmPassword', 'eye-confirm')">
                                <span id="eye-confirm" class="material-icons">visibility_off</span>
                            </button>
                        </div>
                    </div>

                <div class="dlg-actions">
                    <button type="button" class="btn cancel-btn" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn confirm-btn">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="deactivateModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content dlg">
            <div class="dlg-header">
                <h2>Deactivate profile</h2>
            </div>

            <div class="dlg-content text-start">
                <p>Are you sure you want to deactivate your profile?</p>
            </div>

            <div class="dlg-actions">
                <button type="button" class="btn cancel-btn" data-bs-dismiss="modal">Cancel</button>
                <form action="${pageContext.request.contextPath}/profile" method="post">
                    <input type="hidden" name="action" value="deactivate">
                    <input type="hidden" name="clientId" value="<%= client.getId() %>">
                    <button type="submit" class="btn confirm-btn">Yes, deactivate</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function togglePassword(inputId, eyeId) {
        const field = document.getElementById(inputId);
        const eye = document.getElementById(eyeId);

        if (field.type === "password") {
            field.type = "text";
            eye.textContent = "visibility";
        } else {
            field.type = "password";
            eye.textContent = "visibility_off";
        }
    }
</script>
</body>
</html>
