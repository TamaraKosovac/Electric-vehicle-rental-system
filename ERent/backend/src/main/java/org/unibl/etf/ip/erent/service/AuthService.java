package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.unibl.etf.ip.erent.dto.LoginDTO;
import org.unibl.etf.ip.erent.model.Client;
import org.unibl.etf.ip.erent.model.Employee;
import org.unibl.etf.ip.erent.repository.ClientRepository;
import org.unibl.etf.ip.erent.repository.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginDTO login(LoginDTO request) {
        Employee employee = employeeRepository.findByUsername(request.getUsername()).orElse(null);
        if (employee != null && passwordEncoder.matches(request.getPassword(), employee.getPassword())) {
            return new LoginDTO(employee.getUsername(), null, employee.getId(), employee.getRole().name());
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
}
