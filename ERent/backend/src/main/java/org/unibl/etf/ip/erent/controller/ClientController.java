package org.unibl.etf.ip.erent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.erent.model.Client;
import org.unibl.etf.ip.erent.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public List<Client> getAll() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @PostMapping
    public Client create(@RequestBody Client client) {
        return clientService.save(client);
    }

    @PutMapping("/{id}")
    public Client update(@PathVariable Long id, @RequestBody Client client) {
        client.setId(id);
        return clientService.save(client);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }
}
