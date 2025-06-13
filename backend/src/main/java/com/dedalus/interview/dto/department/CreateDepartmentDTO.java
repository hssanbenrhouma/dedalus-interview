package com.dedalus.interview.dto.department;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateDepartmentDTO {
    @NotBlank
    private String name;
}
