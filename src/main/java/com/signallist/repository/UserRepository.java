package com.signallist.repository;

import com.signallist.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  // Returns just the ID as a String wrapped in the projection
  @Query(value = "{ 'email' : ?0 }", fields = "{ 'id' : 1 }")
  Optional<User> findIdByEmail(String email);
}


