package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.unibl.etf.ip.erent.dto.LoginDTO;
import org.unibl.etf.ip.erent.dto.LoginResponseDTO;
import org.unibl.etf.ip.erent.model.Client;
import org.unibl.etf.ip.erent.model.Employee;
import org.unibl.etf.ip.erent.repository.ClientRepository;
import org.unibl.etf.ip.erent.repository.EmployeeRepository;
import org.unibl.etf.ip.erent.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponseDTO login(LoginDTO request) {
        Employee employee = employeeRepository.findByUsername(request.getUsername()).orElse(null);

        if (employee != null && passwordEncoder.matches(request.getPassword(), employee.getPassword())) {
            String token = jwtUtil.generateToken(employee.getUsername(), employee.getRole().name());

            return new LoginResponseDTO(
                    token,
                    employee.getUsername(),
                    employee.getId(),
                    employee.getRole().name()
            );
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
}

