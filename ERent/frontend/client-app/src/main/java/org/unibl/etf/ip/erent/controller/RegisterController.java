package org.unibl.etf.ip.erent.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.unibl.etf.ip.erent.beans.ClientBean;
import org.unibl.etf.ip.erent.dao.ClientDAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@WebServlet("/register")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class RegisterController extends HttpServlet {
    private static final String UPLOAD_DIR = "images" + File.separator + "avatars";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/register.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String appPath = request.getServletContext().getRealPath("/");
        String uploadPath = appPath + UPLOAD_DIR;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        Part filePart = request.getPart("avatar");
        String avatarPath = null;

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            File file = new File(uploadDir, fileName);

            try (var input = filePart.getInputStream()) {
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            avatarPath = "/images/avatars/" + fileName;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String documentType = request.getParameter("documentType");
        String documentNumber = request.getParameter("documentNumber");
        String drivingLicense = request.getParameter("drivingLicense");

        if (!isValidPassword(password)) {
            request.setAttribute("error", "Password must have at least 8 characters, one uppercase letter, one number, and one special character.");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/register.jsp");
            rd.forward(request, response);
            return;
        }

        ClientBean client = new ClientBean(
                0,
                username,
                password,
                firstName,
                lastName,
                documentType,
                documentNumber,
                drivingLicense,
                email,
                phone,
                avatarPath,
                false,
                true
        );

        boolean success = ClientDAO.register(client);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.setAttribute("error", "Username or email already exists!");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/register.jsp");
            rd.forward(request, response);
        }
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-]).{8,}$";
        return password != null && password.matches(regex);
    }
}