package io.tecky.dtos;

import io.tecky.models.Role;

public interface UserContent {
    Integer getId();
    String getUsername();

    Role getRole();
}
