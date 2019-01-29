package com.playground.controllers;

import com.playground.model.entity.Role;
import com.playground.service.RoleService;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class RoleController
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    /** RoleService roleService */
    private final RoleService roleService;

    /**
     * RoleController Constructor
     *
     * @param roleService RoleService
     */
    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * [GET] Return all roles
     *
     * @return ResponseEntity
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
    }

    /**
     * [GET] Return a role
     *
     * @param id int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Role not found
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Role> getRolesById(@PathVariable("id") int id) throws ResourceNotFoundException {
        Role role = roleService.getRole(id);

        if (role == null) {
            throw new ResourceNotFoundException("Role with id " + id + " not found");
        }

        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    /**
     * [POST] Creates a role and return it
     *
     * @param role Role
     *
     * @return ResponseEntity
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return new ResponseEntity<>(roleService.createRole(role), HttpStatus.CREATED);
    }

    /**
     * [PUT] Updates a sport and return it
     *
     * @param id int
     * @param role Role
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Role not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable("id") int id, @RequestBody Role role) throws ResourceNotFoundException {
        Role currentRole = roleService.getRole(id);

        if (currentRole == null) {
            throw new ResourceNotFoundException("Role with id " + id + " not found");
        }

        return new ResponseEntity<>(roleService.updateRole(id, role), HttpStatus.OK);
    }

    /**
     * [DELETE] Remove a role
     *
     * @param id int
     *
     * @return ResponseEntity
     *
     * @throws ResourceNotFoundException Role not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteRole(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
        Role currentRole = roleService.getRole(id);

        if (currentRole == null) {
            throw new ResourceNotFoundException("Role with id " + id + " not found");
        }

        roleService.deleteRole(currentRole);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
