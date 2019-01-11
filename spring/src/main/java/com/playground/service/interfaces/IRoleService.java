package com.playground.service.interfaces;

import com.playground.model.Role;

import java.util.List;

/**
 * Interface IRoleService
 */
public interface IRoleService {

    /**
     * Return all roles
     *
     * @return List<Role>
     */
    List<Role> getRoles();

    /**
     * Return one role
     *
     * @param id int
     *
     * @return Role
     */
    Role getRole(int id);

    /**
     * Create a role and return it
     *
     * @param role Role
     *
     * @return Role
     */
    Role createRole(Role role);

    /**
     * Update a role and return it
     *
     * @param id int
     * @param role Role
     *
     * @return Role
     */
    Role updateRole(int id, Role role);

    /**
     * Delete a role
     *
     * @param role Role
     */
    void deleteRole(Role role);
}
