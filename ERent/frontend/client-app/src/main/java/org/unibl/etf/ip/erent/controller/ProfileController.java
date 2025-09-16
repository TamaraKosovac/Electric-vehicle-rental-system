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
        request.getRequestDispatcher("/pages/profile.jsp").forward(request, response);
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
                response.sendRedirect(request.getContextPath() + "/pages/login.jsp?msg=deactivated");
            } else {
                response.sendRedirect(request.getContextPath()
                        + "/pages/home.jsp?activePage=profile&error=Could not deactivate profile");
            }
        }
    }
}
