package com.signallist.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class User {
  @Id
  private String id;

  private String name;

  private String email;

  private boolean emailVerified;

  private String createdAt;

  private String updatedAt;
}


