package com.signallist.repository;

import com.signallist.dto.UserDTO;
import com.signallist.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  // Returns just the ID as a String wrapped in the projection
  @Query(value = "{ 'email' : ?0 }", fields = "{ 'id' : 1 }")
  Optional<User> findIdByEmail(String email);

  // Returns all users where email and name exist and are not null/empty, as UserDTO
  @Query(value = "{ 'email' : { $exists: true, $ne: null, $ne: '' }, 'name' : { $exists: true, $ne: null, $ne: '' } }",
         fields = "{ '_id' : 1, 'name' : 1, 'email' : 1}")
  List<UserDTO> findAllUsersWithEmail();
}


