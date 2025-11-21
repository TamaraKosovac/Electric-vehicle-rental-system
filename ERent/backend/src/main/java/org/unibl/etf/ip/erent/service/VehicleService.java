package org.unibl.etf.ip.erent.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.erent.dto.VehicleDTO;
import org.unibl.etf.ip.erent.model.*;
import org.unibl.etf.ip.erent.repository.ManufacturerRepository;
import org.unibl.etf.ip.erent.repository.VehicleRepository;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ManufacturerRepository manufacturerRepository;

    public List<VehicleDTO> findAll() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicle -> new VehicleDTO(
                        vehicle.getId(),
                        vehicle.getUniqueId(),
                        vehicle.getModel(),
                        vehicle.getManufacturer().getName(),
                        vehicle.getPurchasePrice(),
                        vehicle.getImagePath(),
                        vehicle.getState(),
                        vehicle.getCurrentLatitude(),
                        vehicle.getCurrentLongitude()
                ))
                .toList();
    }

    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }
    @Transactional
    public int importFromCsv(MultipartFile file) {
        log.info("=== CSV IMPORT STARTED ===");

        int importedCount = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean firstLine = true;
            int lineNum = 0;

            while ((line = reader.readLine()) != null) {
                lineNum++;
                log.info("LINE {}: {}", lineNum, line);

                // Preskoči header
                if (firstLine) {
                    firstLine = false;
                    log.info("Header skipped.");
                    continue;
                }

                String[] row = line.split(",");
                log.info("Row length = {}", row.length);

                if (row.length < 10) {
                    log.error("Row {} malformed! Expected >= 10 columns, got {}", lineNum, row.length);
                    continue;
                }

                String type = clean(row[0]);
                String uniqueId = clean(row[1]);
                log.info("=> Parsed type={}, uniqueId={}", type, uniqueId);

                String manufacturerName = clean(row[2]);
                String country = clean(row[3]);
                String address = clean(row[4]);
                String phone = clean(row[5]);
                String fax = clean(row[6]);
                String email = clean(row[7]);

                String model = clean(row[8]);
                double price = Double.parseDouble(clean(row[9]));

                Vehicle vehicle = null;

                // CAR
                if ("car".equalsIgnoreCase(type)) {
                    log.info("Creating CAR {}", uniqueId);
                    Car car = new Car();
                    car.setUniqueId(uniqueId);
                    car.setModel(model);
                    car.setPurchasePrice(price);
                    car.setMalfunctions(new ArrayList<>());

                    if (row.length > 10 && !row[10].isBlank()) {
                        log.info("Purchase date: {}", row[10]);
                        car.setPurchaseDate(LocalDate.parse(clean(row[10])));
                    }
                    if (row.length > 11 && !row[11].isBlank()) {
                        log.info("Description: {}", row[11]);
                        car.setDescription(clean(row[11]));
                    }

                    vehicle = car;
                }
                // BIKE
                else if ("bike".equalsIgnoreCase(type)) {
                    log.info("Creating BIKE {}", uniqueId);
                    Bike bike = new Bike();
                    bike.setUniqueId(uniqueId);
                    bike.setModel(model);
                    bike.setPurchasePrice(price);
                    bike.setMalfunctions(new ArrayList<>());

                    if (row.length > 12 && !row[12].isBlank()) {
                        log.info("Autonomy: {}", row[12]);
                        bike.setAutonomy(Integer.parseInt(clean(row[12])));
                    }

                    vehicle = bike;
                }
                // SCOOTER
                else if ("scooter".equalsIgnoreCase(type)) {
                    log.info("Creating SCOOTER {}", uniqueId);
                    Scooter scooter = new Scooter();
                    scooter.setUniqueId(uniqueId);
                    scooter.setModel(model);
                    scooter.setPurchasePrice(price);
                    scooter.setMalfunctions(new ArrayList<>());

                    if (row.length > 13 && !row[13].isBlank()) {
                        log.info("Max speed: {}", row[13]);
                        scooter.setMaxSpeed(Integer.parseInt(clean(row[13])));
                    }

                    vehicle = scooter;
                }
                else {
                    log.error("Unknown vehicle type '{}' on line {}", type, lineNum);
                    continue;
                }

                // Image
                if (row.length > 14 && !row[14].isBlank()) {
                    vehicle.setImagePath(clean(row[14]));
                    log.info("Image path = {}", vehicle.getImagePath());
                }

                // State
                if (row.length > 15 && !row[15].isBlank()) {
                    boolean rented = Boolean.parseBoolean(clean(row[15]));
                    vehicle.setState(rented ? VehicleState.RENTED : VehicleState.AVAILABLE);
                } else {
                    vehicle.setState(VehicleState.AVAILABLE);
                }
                log.info("State = {}", vehicle.getState());

                // Manufacturer
                Manufacturer m = getOrCreateManufacturer(manufacturerName, country, address, phone, fax, email);
                vehicle.setManufacturer(m);
                log.info("Manufacturer = {}", m.getName());

                // Malfunctions
                if (row.length > 16 && !row[16].isBlank()) {
                    String[] malfs = row[16].split(";");
                    String[] dates = (row.length > 17) ? row[17].split(";") : new String[0];

                    log.info("Malfunctions count = {}", malfs.length);

                    for (int i = 0; i < malfs.length; i++) {
                        log.info("Adding malfunction: {}", malfs[i]);

                        Malfunction malfunction = new Malfunction();
                        malfunction.setDescription(clean(malfs[i]));

                        if (i < dates.length && !dates[i].isBlank()) {
                            log.info("  date: {}", dates[i]);
                            malfunction.setDateTime(LocalDateTime.parse(clean(dates[i])));
                        } else {
                            malfunction.setDateTime(LocalDateTime.now());
                        }

                        malfunction.setVehicle(vehicle);
                        vehicle.getMalfunctions().add(malfunction);
                    }
                    if (!vehicle.getMalfunctions().isEmpty()) {
                        vehicle.setState(VehicleState.BROKEN);
                    }
                }

                // Location
                if (row.length > 18 && !row[18].isBlank() && row.length > 19 && !row[19].isBlank()) {
                    log.info("Location lat={}, lon={}", row[18], row[19]);
                    vehicle.setCurrentLatitude(Double.parseDouble(clean(row[18])));
                    vehicle.setCurrentLongitude(Double.parseDouble(clean(row[19])));
                } else {
                    vehicle.setCurrentLatitude(44.7722);
                    vehicle.setCurrentLongitude(17.1910);
                }

                log.info("Saving vehicle {}", uniqueId);
                vehicleRepository.save(vehicle);
                importedCount++;
            }

            log.info("=== CSV IMPORT FINISHED: {} vehicles imported ===", importedCount);
            return importedCount;

        } catch (Exception e) {
            log.error("CSV IMPORT ERROR:", e);
            throw new RuntimeException("Error while parsing CSV: " + e.getMessage());
        }
    }


    private Manufacturer getOrCreateManufacturer(String name, String country, String address,
                                                 String phone, String fax, String email) {

        List<Manufacturer> list = manufacturerRepository.findByName(name);

        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }

        Manufacturer newM = new Manufacturer();
        newM.setName(name);
        newM.setCountry(country);
        newM.setAddress(address);
        newM.setPhone(phone);
        newM.setFax(fax);
        newM.setEmail(email);

        return manufacturerRepository.save(newM);
    }

    private String clean(String value) {
        return value == null ? null : value.trim().replace("\"", "");
    }
}