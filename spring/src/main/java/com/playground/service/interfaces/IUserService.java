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
     * Return one user
     *
     * @param username String
     *
     * @return User
     */
    User getUserByUsername(String username);

    /**
     * Return one user
     *
     * @param mail String
     *
     * @return User
     */
    public User getUserByMail(String mail);

    /**
     * Create a user and return it
     *
     * @param user User
     *
     * @return User
     */
    User signup(User user);

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
