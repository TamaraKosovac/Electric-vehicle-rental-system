package org.unibl.etf.ip.erent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.erent.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUsername(String username);
    Optional<Client> findByEmail(String email);
    List<Client> findAllByActiveTrue();
}
