<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>eRent</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register.css">
</head>
<body class="register-body">

<div class="register-container">
    <div class="register-box">
        <div class="register-header">
            <div class="brand">
                <img src="${pageContext.request.contextPath}/images/logo.png" alt="Logo" class="register-logo" />
                <h2 class="register-title">eRent</h2>
                <div class="register-tagline">Easy. Electric. Everywhere.</div>
            </div>
        </div>

        <form method="post" action="${pageContext.request.contextPath}/RegisterServlet" enctype="multipart/form-data" class="register-form">

            <div class="form-row">
                <div class="form-group">
                    <label for="firstName">First name</label>
                    <input type="text" id="firstName" name="firstName" required class="full-width" placeholder="First name"/>
                </div>
                <div class="form-group">
                    <label for="lastName">Last name</label>
                    <input type="text" id="lastName" name="lastName" required class="full-width" placeholder="Last name"/>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" required class="full-width" placeholder="Username"/>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required class="full-width" placeholder="Password"/>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required class="full-width" placeholder="Email"/>
                </div>
                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="text" id="phone" name="phone" required class="full-width" placeholder="Phone"/>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="documentType">Document type</label>
                    <select id="documentType" name="documentType" required class="full-width">
                        <option value="ID_CARD">ID Card</option>
                        <option value="PASSPORT">Passport</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="documentNumber">Document number</label>
                    <input type="text" id="documentNumber" name="documentNumber" required class="full-width" placeholder="Document number"/>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="drivingLicense">Driving license</label>
                    <input type="text" id="drivingLicense" name="drivingLicense" class="full-width" placeholder="Driving license"/>
                </div>

                <div class="form-group">
                    <label class="field-label" for="avatar">Avatar</label>

                    <div id="image-preview" class="image-preview" style="display:none;">
                        <img id="preview-img" src="#" alt="Preview" />
                    </div>

                    <div id="dropzone" class="image-dropzone">
                        <p>Drag & drop image here or click to select</p>
                        <input type="file" id="avatar" name="avatar" accept="image/*" class="file-input"
                               onchange="previewImage(event)"/>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn-register">Register</button>
        </form>

        <div class="login-link">
            Already have an account? <a href="${pageContext.request.contextPath}/pages/login.jsp">Login</a>
        </div>
    </div>
</div>
<script>
    function previewImage(event) {
        const input = event.target;
        const previewDiv = document.getElementById("image-preview");
        const previewImg = document.getElementById("preview-img");

        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = function(e) {
                previewDiv.style.display = "block";
                previewImg.src = e.target.result;
            };
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>
</body>
</html>