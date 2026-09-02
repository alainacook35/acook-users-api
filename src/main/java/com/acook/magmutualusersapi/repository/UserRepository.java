package com.acook.magmutualusersapi.repository;

import com.acook.magmutualusersapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
