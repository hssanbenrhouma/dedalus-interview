package com.dedalus.interview.dto.employee;

import com.dedalus.interview.dto.department.DepartmentDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateEmployeeDTO {
    @NotBlank
    private String fullName;
    public String address;
    public String phoneNumber;
    @Email
    public String email;
    public DepartmentDTO department;
}
