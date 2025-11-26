package org.unibl.etf.ip.erent.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.unibl.etf.ip.erent.dao.ClientDAO;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("deactivate".equals(action)) {
            int clientId = Integer.parseInt(request.getParameter("clientId"));

            boolean success = ClientDAO.deactivate(clientId);

            if (success) {
                request.getSession().invalidate();
                response.sendRedirect(request.getContextPath() + "/login?msg=deactivated");
            } else {
                response.sendRedirect(request.getContextPath()
                        + "/home?activePage=profile&error=Could not deactivate profile");
            }
        } else if ("changePassword".equals(action)) {
            int clientId = Integer.parseInt(request.getParameter("clientId"));
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (!newPassword.equals(confirmPassword)) {
                response.sendRedirect(request.getContextPath()
                        + "/home?activePage=profile&error=Passwords do not match");
                return;
            }

            boolean success = ClientDAO.changePassword(clientId, oldPassword, newPassword);

            if (success) {
                response.sendRedirect(request.getContextPath()
                        + "/home?activePage=profile&msg=Password changed successfully");
            } else {
                response.sendRedirect(request.getContextPath()
                        + "/home?activePage=profile&error=Invalid old password");
            }
        }
    }
}