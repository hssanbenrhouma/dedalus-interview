package com.dedalus.interview.dto.department;

import com.dedalus.interview.model.Department;
import lombok.Data;

@Data
public class DepartmentDTO {
    public Long id;
    public String name;
    public int employeeCount;

    public static DepartmentDTO fromEntity(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.id = department.id;
        dto.name = department.name;
        dto.employeeCount = department.employeeCount;
        return dto;
    }
}
