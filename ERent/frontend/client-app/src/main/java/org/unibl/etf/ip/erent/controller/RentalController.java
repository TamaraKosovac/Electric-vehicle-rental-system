package org.unibl.etf.ip.erent.controller;

import org.unibl.etf.ip.erent.dao.RentalDAO;
import org.unibl.etf.ip.erent.dao.RentalPriceDAO;
import org.unibl.etf.ip.erent.dto.RentalPriceDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@WebServlet("/rental")
public class RentalController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("end".equals(action)) {
            Long rentalId = (Long) req.getSession().getAttribute("rentalId");
            Double pricePerHour = (Double) req.getSession().getAttribute("pricePerHour");

            String latStr = req.getParameter("latitude");
            String lonStr = req.getParameter("longitude");

            if (latStr == null || latStr.isEmpty() || lonStr == null || lonStr.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Coordinates missing");
                return;
            }

            double endLat = Double.parseDouble(latStr);
            double endLon = Double.parseDouble(lonStr);

            RentalPriceDTO price = RentalPriceDAO.getByVehicleType("CAR");

            if (rentalId != null && price != null) {
                RentalDAO.finishRental(rentalId, endLat, endLon, price.getPricePerHour());

                resp.setContentType("application/pdf");
                resp.setHeader("Content-Disposition", "attachment; filename=invoice_" + rentalId + ".pdf");

                try (OutputStream out = resp.getOutputStream()) {
                    Document document = new Document();
                    PdfWriter.getInstance(document, out);
                    document.open();

                    document.add(new Paragraph("Rental Invoice"));
                    document.add(new Paragraph("Date: " + LocalDateTime.now()));
                    document.add(new Paragraph("Rental ID: " + rentalId));
                    document.add(new Paragraph("Price per hour: " + pricePerHour + " KM"));
                    document.add(new Paragraph("Final price was calculated and stored in DB."));
                    document.add(new Paragraph("End location: " + endLat + ", " + endLon));

                    document.close();
                }
            }

            req.getSession().removeAttribute("rentalId");
            req.getSession().removeAttribute("pricePerHour");

        } else {
            int clientId = Integer.parseInt(req.getParameter("clientId"));
            Long vehicleId = Long.parseLong(req.getParameter("vehicleId"));
            double lat = Double.parseDouble(req.getParameter("latitude"));
            double lon = Double.parseDouble(req.getParameter("longitude"));

            RentalPriceDTO price = RentalPriceDAO.getByVehicleType("CAR");

            if (price != null) {
                Long rentalId = RentalDAO.startRental(clientId, vehicleId, lat, lon, price.getPricePerHour());
                req.getSession().setAttribute("rentalId", rentalId);
                req.getSession().setAttribute("pricePerHour", price.getPricePerHour());
                resp.sendRedirect("pages/ride.jsp");
            } else {
                resp.sendRedirect("error.jsp");
            }
        }
    }
}