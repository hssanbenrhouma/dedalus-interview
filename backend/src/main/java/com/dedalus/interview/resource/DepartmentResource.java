package com.dedalus.interview.resource;

import com.dedalus.interview.dto.department.CreateDepartmentDTO;
import com.dedalus.interview.dto.department.DepartmentDTO;
import com.dedalus.interview.service.DepartmentService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    @Inject
    DepartmentService departmentService;

    @GET
    public List<DepartmentDTO> list() {
        return departmentService.getAllAsDTO();
    }

    @GET
    @Path("/{id}")
    public DepartmentDTO get(@PathParam("id") Long id) {
        return departmentService.findById(id);
    }

    @POST
    public Response create(@Valid CreateDepartmentDTO createDepartmentDTO) {
        DepartmentDTO created = departmentService.create(createDepartmentDTO);
        return Response.status(Response.Status.CREATED).entity(created).build();    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        departmentService.delete(id);
        return Response.noContent().build();
    }
}
