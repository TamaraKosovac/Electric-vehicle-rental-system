package org.unibl.etf.ip.erent.dto;

import lombok.Data;
import org.unibl.etf.ip.erent.model.Employee;
import org.unibl.etf.ip.erent.model.EmployeeRole;

@Data
public class EmployeeDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private EmployeeRole employee;
}
