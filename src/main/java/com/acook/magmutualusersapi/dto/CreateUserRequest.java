package com.acook.magmutualusersapi.dto;

public record CreateUserRequest(
        String firstName,
        String lastName,
        String email,
        String profession,
        String country,
        String city
) {}
