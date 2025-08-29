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
        return clientRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public Client save(Client client) {
        if (client.getPassword() != null) {
            client.setPassword(passwordEncoder.encode(client.getPassword()));
        }
        return clientRepository.save(client);
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}
