package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.unibl.etf.ip.erent.dto.RentalDTO;
import org.unibl.etf.ip.erent.model.Client;
import org.unibl.etf.ip.erent.model.Rental;
import org.unibl.etf.ip.erent.model.Vehicle;
import org.unibl.etf.ip.erent.repository.ClientRepository;
import org.unibl.etf.ip.erent.repository.RentalRepository;
import org.unibl.etf.ip.erent.repository.VehicleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;
    private final ClientRepository clientRepository;

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

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
}
