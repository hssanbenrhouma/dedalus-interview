package com.dedalus.interview.service;

import com.dedalus.interview.dto.department.CreateDepartmentDTO;
import com.dedalus.interview.dto.department.DepartmentDTO;
import com.dedalus.interview.exception.ResourceNotFoundException;
import com.dedalus.interview.model.Department;
import com.dedalus.interview.repository.DepartmentRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class DepartmentServiceTest {

    @Inject
    DepartmentService departmentService;

    @Inject
    DepartmentRepository departmentRepository;

    @Test
    @Transactional
    public void testCreateDepartment() {
        // Given
        CreateDepartmentDTO dto = new CreateDepartmentDTO();
        dto.setName("Research");

        // When
        DepartmentDTO created = departmentService.create(dto);

        // Then
        Assertions.assertNotNull(created);
        Assertions.assertEquals("Research", created.getName());

        // Optional: check the entity directly
        Department department = departmentRepository.findById(created.getId());
        Assertions.assertEquals("Research", department.getName());
    }

    @Test
    @Transactional
    public void testFindById_NotFound() {
        Long nonExistingId = 99999L;

        var exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> departmentService.findById(nonExistingId)
        );

        Assertions.assertEquals("Department not found", exception.getMessage());
    }
}
