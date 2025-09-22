package org.unibl.etf.ip.erent.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.unibl.etf.ip.erent.dao.ClientDAO;
import org.unibl.etf.ip.erent.dto.ClientDTO;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/pages/login.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ClientDTO client = ClientDAO.login(username, password);

        if (client != null) {
            HttpSession session = request.getSession();
            session.setAttribute("client", client);

            response.sendRedirect(request.getContextPath() + "/pages/home.jsp");
        } else {
            request.setAttribute("error", "Invalid username or password!");
            RequestDispatcher rd = request.getRequestDispatcher("/pages/login.jsp");
            rd.forward(request, response);
        }
    }
}