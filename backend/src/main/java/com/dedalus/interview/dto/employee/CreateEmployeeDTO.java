package com.dedalus.interview.dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateEmployeeDTO {
    @NotBlank
    private String fullName;
    public String address;
    public String phoneNumber;
    @Email
    public String email;
    private Long departmentId;
}
