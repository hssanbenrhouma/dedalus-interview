package com.dedalus.interview.resource;

import com.dedalus.interview.dto.employee.CreateEmployeeDTO;
import com.dedalus.interview.dto.employee.EmployeeDTO;
import com.dedalus.interview.dto.employee.UpdateEmployeeDTO;
import com.dedalus.interview.service.EmployeeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @Inject
    EmployeeService employeeService;

    @GET
    public List<EmployeeDTO> listAll() {
        return employeeService.getAllAsDTO();
    }

    @GET
    @Path("/unassigned")
    public List<EmployeeDTO> listUnassigned() {
        return employeeService.findUnassignedAsDTO();
    }

    @GET
    @Path("/department/{deptId}")
    public List<EmployeeDTO> listByDepartment(@PathParam("deptId") Long deptId) {
        return employeeService.findByDepartment(deptId);
    }

    @GET
    @Path("/search")
    public List<EmployeeDTO> searchByName(@QueryParam("name") String name) {
        return employeeService.searchByName(name);
    }

    @GET
    @Path("/{id}")
    public EmployeeDTO get(@PathParam("id") Long id) {
        return employeeService.findById(id);
    }

    @POST
    public EmployeeDTO create(CreateEmployeeDTO createEmployeeDTO) {
        return employeeService.create(createEmployeeDTO);
    }

    @PUT
    @Path("/{id}")
    public EmployeeDTO update(@PathParam("id") Long id, UpdateEmployeeDTO updateEmployeeDTO) {
        return employeeService.update(id, updateEmployeeDTO);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        employeeService.delete(id);
    }

    @DELETE
    @Path("/unassigned")
    public void deleteUnassignedEmployees() {
        employeeService.deleteUnassignedEmployees();
    }
}
