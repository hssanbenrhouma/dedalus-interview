package com.dedalus.interview.service;

import com.dedalus.interview.dto.department.CreateDepartmentDTO;
import com.dedalus.interview.dto.department.DepartmentDTO;
import com.dedalus.interview.exception.ResourceNotFoundException;
import com.dedalus.interview.model.Department;
import com.dedalus.interview.model.Employee;
import com.dedalus.interview.repository.DepartmentRepository;
import com.dedalus.interview.repository.EmployeeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DepartmentService {

    @Inject
    DepartmentRepository departmentRepository;

    @Inject
    EmployeeRepository employeeRepository;

    public List<DepartmentDTO> getAllAsDTO() {
        return departmentRepository.listAll().stream()
                .map(DepartmentDTO::fromEntity)
                .toList();
    }

    public DepartmentDTO findById(Long id) {
        Department department = departmentRepository.findById(id);
        if (department == null) {
            throw new ResourceNotFoundException("Department not found");
        }
        return DepartmentDTO.fromEntity(department);    }

    @Transactional
    public DepartmentDTO create(CreateDepartmentDTO createDepartmentDTO) {
        Department department = new Department();
        department.setName(createDepartmentDTO.getName());
        departmentRepository.persist(department);
        return DepartmentDTO.fromEntity(department);
    }

    @Transactional
    public void delete(Long id) {
        Department department = departmentRepository.findById(id);
        if (department == null) {
            throw new ResourceNotFoundException("Department not found");
        }
        List<Employee> employees = employeeRepository.findByDepartmentId(id);
        employees.forEach(e -> e.department = null);
        departmentRepository.delete(department);
    }
}