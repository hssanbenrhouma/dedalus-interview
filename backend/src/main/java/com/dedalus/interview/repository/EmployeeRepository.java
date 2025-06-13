package com.dedalus.interview.repository;

import com.dedalus.interview.model.Employee;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {

    public List<Employee> findByDepartmentId(Long deptId) {
        return list("department.id", deptId);
    }

    public List<Employee> findUnassigned() {
        return list("department IS NULL");
    }

    public List<Employee> searchByName(String name) {
        return list("LOWER(fullName) LIKE LOWER(?1)", "%" + name + "%");
    }
}