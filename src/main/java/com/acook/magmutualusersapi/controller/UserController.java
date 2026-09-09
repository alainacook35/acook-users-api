package com.acook.magmutualusersapi.controller;

import com.acook.magmutualusersapi.dto.CreateUserRequest;
import com.acook.magmutualusersapi.entity.User;
import com.acook.magmutualusersapi.repository.UserRepository;
import com.acook.magmutualusersapi.spec.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(
            // Search covers firstName, lastName, and email
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String profession,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @PageableDefault(sort = "lastName") Pageable pageable
    ) {
        Specification<User> spec = UserSpecification.filterUsers(search, profession, country, city, startDate, endDate);

        Page<User> allUsers = userRepository.findAll(spec, pageable);

        return ResponseEntity.ok().body(allUsers);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest userRequest) {
        User userToCreate = new User(
                userRequest.firstName(),
                userRequest.lastName(),
                userRequest.email(),
                userRequest.profession(),
                userRequest.country(),
                userRequest.city()
        );

        User createdUser = userRepository.save(userToCreate);

        return ResponseEntity.ok().body(createdUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
