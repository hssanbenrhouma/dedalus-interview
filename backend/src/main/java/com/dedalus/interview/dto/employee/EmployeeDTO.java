package com.dedalus.interview.dto.employee;

import com.dedalus.interview.dto.department.DepartmentDTO;
import com.dedalus.interview.model.Employee;
import lombok.Data;

@Data
public class EmployeeDTO {
    public Long id;
    public String fullName;
    public String address;
    public String phoneNumber;
    public String email;
    public DepartmentDTO department;

    public static EmployeeDTO fromEntity(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.id = employee.id;
        dto.fullName = employee.fullName;
        dto.address = employee.address;
        dto.phoneNumber = employee.phoneNumber;
        dto.email = employee.email;
        if (employee.department != null) {
            dto.department = DepartmentDTO.fromEntity(employee.department);
        }
        return dto;
    }
}
