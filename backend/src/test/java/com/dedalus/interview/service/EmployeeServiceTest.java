package com.dedalus.interview.service;

import com.dedalus.interview.dto.department.CreateDepartmentDTO;
import com.dedalus.interview.dto.department.DepartmentDTO;
import com.dedalus.interview.dto.employee.CreateEmployeeDTO;
import com.dedalus.interview.dto.employee.EmployeeDTO;
import com.dedalus.interview.exception.ResourceNotFoundException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class EmployeeServiceTest {

    @Inject
    EmployeeService employeeService;

    @Inject
    DepartmentService departmentService;

    @Test
    @Transactional
    public void testCreateEmployee() {

        // add department
        CreateDepartmentDTO departmentDTO = new CreateDepartmentDTO();
        departmentDTO.setName("IT");
        DepartmentDTO createdDepartment = departmentService.create(departmentDTO);

        // verify department creation
        Assertions.assertNotNull(createdDepartment);
        Assertions.assertEquals("IT", createdDepartment.getName());


        // Arrange
        CreateEmployeeDTO dto = new CreateEmployeeDTO();
        dto.setFullName("Alice Johnson");
        dto.setEmail("alice@example.com");
        dto.setPhoneNumber("0606060606");
        dto.setAddress("123 Rue Exemple");
        dto.setDepartmentId(createdDepartment.getId());

        // Act
        EmployeeDTO result = employeeService.create(dto);

        // Assert
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("Alice Johnson", result.getFullName());
        Assertions.assertEquals("alice@example.com", result.getEmail());
    }

    @Test
    @Transactional
    public void testFindEmployeeById_NotFound() {
        Long fakeId = 99999L;

        var exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> employeeService.findById(fakeId)
        );

        Assertions.assertEquals("Employee not found", exception.getMessage());
    }
}