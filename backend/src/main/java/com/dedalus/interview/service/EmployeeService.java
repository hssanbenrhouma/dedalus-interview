package com.dedalus.interview.service;

import com.dedalus.interview.dto.employee.CreateEmployeeDTO;
import com.dedalus.interview.dto.employee.EmployeeDTO;
import com.dedalus.interview.dto.employee.UpdateEmployeeDTO;
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
public class EmployeeService {

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    DepartmentRepository departmentRepository;

    public List<EmployeeDTO> getAllAsDTO() {
        return employeeRepository.listAll().stream()
                .map(EmployeeDTO::fromEntity)
                .toList();
    }

    public EmployeeDTO findById(Long id) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found");
        }
        return EmployeeDTO.fromEntity(employee);
    }
    public List<EmployeeDTO> findByDepartment(Long deptId) {
        return employeeRepository.findByDepartmentId(deptId).stream()
                .map(EmployeeDTO::fromEntity)
                .toList();
    }

    public List<EmployeeDTO> findUnassignedAsDTO() {
        return employeeRepository.findUnassigned().stream()
                .map(EmployeeDTO::fromEntity)
                .toList();
    }

    public List<EmployeeDTO> searchByName(String name) {
        return employeeRepository.searchByName(name).stream()
                .map(EmployeeDTO::fromEntity)
                .toList();
    }

    @Transactional
    public EmployeeDTO create(CreateEmployeeDTO createEmployeeDTO) {
        Employee employee = new Employee();
        employee.setFullName(createEmployeeDTO.getFullName());
        employee.setAddress(createEmployeeDTO.getAddress());
        employee.setPhoneNumber(createEmployeeDTO.getPhoneNumber());
        employee.setEmail(createEmployeeDTO.getEmail());

        if (createEmployeeDTO.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(createEmployeeDTO.getDepartmentId());
            if (dept == null) {
                throw new ResourceNotFoundException("Department not found");
            }
            employee.setDepartment(dept);
            dept.setEmployeeCount(dept.getEmployeeCount() + 1);
        }

        employeeRepository.persist(employee);
        return EmployeeDTO.fromEntity(employee);
    }

    @Transactional
    public EmployeeDTO update(Long id, UpdateEmployeeDTO updateEmployeeDTO) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found");
        }

        employee.setFullName(updateEmployeeDTO.getFullName());
        employee.setAddress(updateEmployeeDTO.getAddress());
        employee.setPhoneNumber(updateEmployeeDTO.getPhoneNumber());
        employee.setEmail(updateEmployeeDTO.getEmail());

        if (updateEmployeeDTO.getDepartment() != null) {
            Department newDept = departmentRepository.findById(updateEmployeeDTO.getDepartment().getId());
            if (newDept == null) {
                throw new ResourceNotFoundException("Department not found");
            }

            Department currentDept = employee.getDepartment();
            if (currentDept != null) {
                currentDept.setEmployeeCount(currentDept.getEmployeeCount() - 1);
            }

            newDept.setEmployeeCount(newDept.getEmployeeCount() + 1);
            employee.setDepartment(newDept);
        }
        employeeRepository.persist(employee);
        return EmployeeDTO.fromEntity(employee);
    }

    @Transactional
    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found");
        }

        Department dept = employee.getDepartment();
        if (dept != null) {
            dept.setEmployeeCount(dept.getEmployeeCount() - 1);
        }
        employeeRepository.delete(employee);
    }

    @Transactional
    public void deleteUnassignedEmployees() {
        List<Employee> unassignedEmployees = employeeRepository.findUnassigned();
        for (Employee employee : unassignedEmployees) {
            employeeRepository.delete(employee);
        }
    }
}
