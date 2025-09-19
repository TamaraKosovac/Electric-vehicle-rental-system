package org.unibl.etf.ip.erent.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.nio.file.*;
import org.unibl.etf.ip.erent.dao.ClientDAO;

@WebServlet("/clientphotocontroller")
public class ClientPhotoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String clientIdParam = req.getParameter("clientId");
        if (clientIdParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        final int clientId;
        try {
            clientId = Integer.parseInt(clientIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String pathFromDb = ClientDAO.getAvatarPathById(clientId);
        if (pathFromDb == null || pathFromDb.isBlank()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (pathFromDb.startsWith("/")) {
            String mime = getServletContext().getMimeType(pathFromDb);
            if (mime == null) mime = "image/jpeg";
            resp.setContentType(mime);
            resp.setHeader("Cache-Control", "public, max-age=86400");

            try (InputStream in = getServletContext().getResourceAsStream(pathFromDb)) {
                if (in != null) {
                    in.transferTo(resp.getOutputStream());
                    return;
                }
            }

            String real = getServletContext().getRealPath(pathFromDb);
            if (real != null) {
                Path file = Paths.get(real).normalize();
                if (Files.exists(file) && Files.isRegularFile(file)) {
                    try (OutputStream out = resp.getOutputStream()) {
                        Files.copy(file, out);
                    }
                    return;
                }
            }

            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Path file = Paths.get(pathFromDb).normalize();
        if (!file.isAbsolute()) {
            Path base = Paths.get(System.getProperty("user.home"), "erent", "uploads").toAbsolutePath().normalize();
            file = base.resolve(file).normalize();
        }
        if (!Files.exists(file) || !Files.isRegularFile(file)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mime = Files.probeContentType(file);
        if (mime == null || !mime.startsWith("image/")) mime = "image/jpeg";
        resp.setContentType(mime);
        resp.setHeader("Cache-Control", "public, max-age=86400");

        try (OutputStream out = resp.getOutputStream()) {
            Files.copy(file, out);
        }
    }
}