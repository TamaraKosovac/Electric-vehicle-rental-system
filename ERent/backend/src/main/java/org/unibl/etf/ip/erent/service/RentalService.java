package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.unibl.etf.ip.erent.dto.DailyRevenueDTO;
import org.unibl.etf.ip.erent.dto.RentalDTO;
import org.unibl.etf.ip.erent.dto.RentalDetailsDTO;
import org.unibl.etf.ip.erent.model.Client;
import org.unibl.etf.ip.erent.model.Rental;
import org.unibl.etf.ip.erent.model.Vehicle;
import org.unibl.etf.ip.erent.repository.ClientRepository;
import org.unibl.etf.ip.erent.repository.RentalRepository;
import org.unibl.etf.ip.erent.repository.VehicleRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;
    private final ClientRepository clientRepository;


    public Rental findById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found"));
    }

    public Rental createFromDTO(RentalDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        Rental rental = new Rental();
        rental.setStartDateTime(dto.getStartDateTime());
        rental.setEndDateTime(dto.getEndDateTime());
        rental.setDuration(dto.getDuration());
        rental.setStartLatitude(dto.getStartLatitude());
        rental.setStartLongitude(dto.getStartLongitude());
        rental.setEndLatitude(dto.getEndLatitude());
        rental.setEndLongitude(dto.getEndLongitude());
        rental.setPrice(dto.getPrice());
        rental.setVehicle(vehicle);
        rental.setClient(client);

        return rentalRepository.save(rental);
    }

    public Rental updateFromDTO(Long id, RentalDTO dto) {
        Rental rental = findById(id);

        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        rental.setStartDateTime(dto.getStartDateTime());
        rental.setEndDateTime(dto.getEndDateTime());
        rental.setDuration(dto.getDuration());
        rental.setStartLatitude(dto.getStartLatitude());
        rental.setStartLongitude(dto.getStartLongitude());
        rental.setEndLatitude(dto.getEndLatitude());
        rental.setEndLongitude(dto.getEndLongitude());
        rental.setPrice(dto.getPrice());
        rental.setVehicle(vehicle);
        rental.setClient(client);

        return rentalRepository.save(rental);
    }

    public void delete(Long id) {
        if (!rentalRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found");
        }
        rentalRepository.deleteById(id);
    }

    public List<RentalDetailsDTO> findByVehicleId(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));

        List<Rental> rentals = rentalRepository.findByVehicle(vehicle);

        return rentals.stream().map(rental -> {
            RentalDetailsDTO dto = new RentalDetailsDTO();
            dto.setStartDateTime(rental.getStartDateTime());
            dto.setEndDateTime(rental.getEndDateTime());
            dto.setDuration(rental.getDuration());
            dto.setStartLatitude(rental.getStartLatitude());
            dto.setStartLongitude(rental.getStartLongitude());
            dto.setEndLatitude(rental.getEndLatitude());
            dto.setEndLongitude(rental.getEndLongitude());
            dto.setPrice(rental.getPrice());
            dto.setClientFirstName(rental.getClient().getFirstName());
            dto.setClientLastName(rental.getClient().getLastName());
            dto.setManufacturerName(rental.getVehicle().getManufacturer().getName());
            dto.setVehicleModel(rental.getVehicle().getModel());
            return dto;
        }).toList();
    }

    public List<RentalDetailsDTO> findAllDto() {
        return rentalRepository.findAll()
                .stream()
                .map(rental -> {
                    RentalDetailsDTO dto = new RentalDetailsDTO();
                    dto.setStartDateTime(rental.getStartDateTime());
                    dto.setEndDateTime(rental.getEndDateTime());
                    dto.setDuration(rental.getDuration());
                    dto.setStartLatitude(rental.getStartLatitude());
                    dto.setStartLongitude(rental.getStartLongitude());
                    dto.setEndLatitude(rental.getEndLatitude());
                    dto.setEndLongitude(rental.getEndLongitude());
                    dto.setPrice(rental.getPrice());
                    dto.setClientFirstName(rental.getClient().getFirstName());
                    dto.setClientLastName(rental.getClient().getLastName());
                    dto.setManufacturerName(rental.getVehicle().getManufacturer().getName());
                    dto.setVehicleModel(rental.getVehicle().getModel());
                    return dto;
                })
                .toList();
    }

    public List<DailyRevenueDTO> getDailyRevenue(int year, int month) {
        YearMonth ym = YearMonth.of(year, month);

        return rentalRepository.findAll().stream()
                .filter(r -> r.getEndDateTime() != null)
                .filter(r -> {
                    YearMonth rentalMonth = YearMonth.from(r.getEndDateTime());
                    return rentalMonth.equals(ym);
                })
                .collect(Collectors.groupingBy(
                        r -> r.getEndDateTime().toLocalDate(),
                        Collectors.summingDouble(Rental::getPrice)
                ))
                .entrySet().stream()
                .map(e -> new DailyRevenueDTO(e.getKey(), e.getValue()))
                .sorted((a, b) -> a.getDate().compareTo(b.getDate()))
                .toList();
    }
}
