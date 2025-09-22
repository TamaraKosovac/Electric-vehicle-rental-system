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
        int importedCount = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean firstLine = true;
            int lineNum = 0;

            while ((line = reader.readLine()) != null) {
                lineNum++;

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] row = line.split(",");

                String type = clean(row[0]);
                String uniqueId = clean(row[1]);
                String manufacturerName = clean(row[2]);
                String country = clean(row[3]);
                String address = clean(row[4]);
                String phone = clean(row[5]);
                String fax = clean(row[6]);
                String email = clean(row[7]);

                String model = clean(row[8]);
                double price = Double.parseDouble(clean(row[9]));

                Vehicle vehicle = null;

                if ("car".equalsIgnoreCase(type)) {
                    Car car = new Car();
                    car.setUniqueId(uniqueId);
                    car.setModel(model);
                    car.setPurchasePrice(price);

                    if (row.length > 10 && !row[10].isBlank()) {
                        car.setPurchaseDate(LocalDate.parse(clean(row[10])));
                    }
                    if (row.length > 11 && !row[11].isBlank()) {
                        car.setDescription(clean(row[11]));
                    }

                    vehicle = car;

                } else if ("bike".equalsIgnoreCase(type)) {
                    Bike bike = new Bike();
                    bike.setUniqueId(uniqueId);
                    bike.setModel(model);
                    bike.setPurchasePrice(price);

                    if (row.length > 12 && !row[12].isBlank()) {
                        bike.setAutonomy(Integer.parseInt(clean(row[12])));
                    }

                    vehicle = bike;

                } else if ("scooter".equalsIgnoreCase(type)) {
                    Scooter scooter = new Scooter();
                    scooter.setUniqueId(uniqueId);
                    scooter.setModel(model);
                    scooter.setPurchasePrice(price);

                    if (row.length > 13 && !row[13].isBlank()) {
                        scooter.setMaxSpeed(Integer.parseInt(clean(row[13])));
                    }

                    vehicle = scooter;

                } else {
                    continue;
                }

                if (row.length > 14 && !row[14].isBlank()) {
                    vehicle.setImagePath(clean(row[14]));
                }
                if (row.length > 15 && !row[15].isBlank()) {
                    boolean rented = Boolean.parseBoolean(clean(row[15]));
                    vehicle.setState(rented ? VehicleState.RENTED : VehicleState.AVAILABLE);
                } else {
                    vehicle.setState(VehicleState.AVAILABLE);
                }

                Manufacturer m = getOrCreateManufacturer(manufacturerName, country, address, phone, fax, email);
                vehicle.setManufacturer(m);

                if (row.length > 16 && !row[16].isBlank()) {
                    String[] malfs = row[16].split(";");
                    String[] dates = (row.length > 17) ? row[17].split(";") : new String[0];

                    for (int i = 0; i < malfs.length; i++) {
                        Malfunction malfunction = new Malfunction();
                        malfunction.setDescription(clean(malfs[i]));

                        if (i < dates.length && !dates[i].isBlank()) {
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

                if (row.length > 18 && !row[18].isBlank() && row.length > 19 && !row[19].isBlank()) {
                    vehicle.setCurrentLatitude(Double.parseDouble(clean(row[18])));
                    vehicle.setCurrentLongitude(Double.parseDouble(clean(row[19])));
                } else {
                    vehicle.setCurrentLatitude(44.7722);
                    vehicle.setCurrentLongitude(17.1910);
                }

                vehicleRepository.save(vehicle);
                importedCount++;
            }

            return importedCount;

        } catch (Exception e) {
            throw new RuntimeException("Error while parsing CSV", e);
        }
    }

    private Manufacturer getOrCreateManufacturer(String name, String country, String address,
                                                 String phone, String fax, String email) {
        return manufacturerRepository.findByName(name)
                .orElseGet(() -> {
                    Manufacturer newM = new Manufacturer();
                    newM.setName(name);
                    newM.setCountry(country);
                    newM.setAddress(address);
                    newM.setPhone(phone);
                    newM.setFax(fax);
                    newM.setEmail(email);
                    return manufacturerRepository.save(newM);
                });
    }

    private String clean(String value) {
        return value == null ? null : value.trim().replace("\"", "");
    }
}