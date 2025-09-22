package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.erent.model.Client;
import org.unibl.etf.ip.erent.repository.ClientRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public Client save(Client client) {
        if (client.getUsername() == null || client.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required.");
        }
        if (client.getFirstName() == null || client.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name is required.");
        }
        if (client.getLastName() == null || client.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name is required.");
        }
        if (client.getEmail() == null || !isValidEmail(client.getEmail())) {
            throw new IllegalArgumentException("Valid email is required.");
        }

        if (client.getPassword() != null) {
            if (!isValidPassword(client.getPassword())) {
                throw new IllegalArgumentException("Password must be at least 8 characters long, contain one uppercase letter, one number, and one special character.");
            }
            client.setPassword(passwordEncoder.encode(client.getPassword()));
        }

        return clientRepository.save(client);
    }

    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found");
        }
        clientRepository.deleteById(id);
    }

    public Client blockClient(Long id) {
        Client client = findById(id);
        client.setBlocked(true);
        return clientRepository.save(client);
    }

    public Client unblockClient(Long id) {
        Client client = findById(id);
        client.setBlocked(false);
        return clientRepository.save(client);
    }

    public Client activateClient(Long id) {
        Client client = findById(id);
        client.setActive(true);
        return clientRepository.save(client);
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }
}