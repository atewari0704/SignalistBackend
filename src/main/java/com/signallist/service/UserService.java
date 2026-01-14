package com.signallist.service;

import com.signallist.dto.UserDTO;
import com.signallist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    public final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Fetches all users with email and name as UserDTO objects.
     * This replicates the functionality of the Node.js code:
     * - Queries users where both email and name exist and are not null/empty (done at DB level)
     * - Returns only id, email, and name (projected at DB level)
     *
     * @return List of UserDTO objects
     */
    public List<UserDTO> getAllUsersWithEmail() {
        try {
            return repository.findAllUsersWithEmail();
        } catch (Exception e) {
            System.err.println("Failed to get all users: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Return empty list on error
        }
    }
}

