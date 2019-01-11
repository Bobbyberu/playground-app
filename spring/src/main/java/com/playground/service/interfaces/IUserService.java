package com.playground.service.interfaces;

import com.playground.model.User;

import java.util.List;

/**
 * Interface IUserService
 */
public interface IUserService {

    /**
     * Return all users
     *
     * @return List<User>
     */
    List<User> getUsers();

    /**
     * Return one user
     *
     * @param id int
     *
     * @return User
     */
    User getUser(int id);

    /**
     * Create a user and return it
     *
     * @param user User
     *
     * @return User
     */
    User createUser(User user);

    /**
     * Update a user and return it
     *
     * @param id int
     * @param user User
     *
     * @return User
     */
    User updateUser(int id, User user);

    /**
     * Delete a user
     *
     * @param user User
     */
    void deleteUser(User user);
}
