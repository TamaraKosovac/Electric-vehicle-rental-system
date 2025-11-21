package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.erent.model.Employee;
import org.unibl.etf.ip.erent.repository.EmployeeRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public Employee save(Employee employee) {
        if (employee.getUsername() == null || employee.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        if (employee.getRole() == null) {
            throw new IllegalArgumentException("Role must be specified");
        }

        if (employee.getId() != null && employee.getId() > 0) {
            Employee existing = employeeRepository.findById(employee.getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            if (employee.getPassword() == null || employee.getPassword().isBlank()) {
                employee.setPassword(existing.getPassword());
            } else {
                if (!isValidPassword(employee.getPassword())) {
                    throw new IllegalArgumentException(
                            "Password must be at least 8 characters long, contain one uppercase letter, one number, and one special character."
                    );
                }
                employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            }
        } else {
            if (employee.getPassword() == null || employee.getPassword().isBlank()) {
                throw new IllegalArgumentException("Password cannot be empty");
            }
            if (!isValidPassword(employee.getPassword())) {
                throw new IllegalArgumentException(
                        "Password must be at least 8 characters long, contain one uppercase letter, one number, and one special character."
                );
            }
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        }

        return employeeRepository.save(employee);
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }

    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }
}