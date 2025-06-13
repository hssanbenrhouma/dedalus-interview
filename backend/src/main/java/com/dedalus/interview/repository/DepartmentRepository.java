package com.dedalus.interview.repository;

import com.dedalus.interview.model.Department;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DepartmentRepository implements PanacheRepository<Department> {

    public Department findByName(String name) {
        return find("name", name).firstResult();
    }
}
