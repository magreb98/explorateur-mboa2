package com.explorateurmboa.user_management.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.explorateurmboa.user_management.repositories.RoleRepository;
import com.explorateurmboa.user_management.models.ERole;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.explorateurmboa.user_management.models.Role;
import com.explorateurmboa.user_management.models.User;
import com.explorateurmboa.user_management.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
class UserController {

    @Autowired
    UserRepository repository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        try {
            List<User> items = new ArrayList<User>();

            repository.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id) {
        Optional<User> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody UpdateRequest item) {
    Optional<User> existingItemOptional = repository.findById(id);

    if (existingItemOptional.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    User existingItem = existingItemOptional.get();

    if (item.getPrenom() != null) existingItem.setPrenom(item.getPrenom());
    if (item.getStatut() != null) existingItem.setStatus(item.getStatut());
    if (item.getTelephone() != null) existingItem.setTelephone(item.getTelephone());
    if (item.getUsername() != null) existingItem.setUsername(item.getUsername());
    if (item.getEmail() != null) existingItem.setEmail(item.getEmail());
    if (item.getPassword() != null && !item.getPassword().isEmpty() 
        && !encoder.matches(item.getPassword(), existingItem.getPassword())) {
        existingItem.setPassword(encoder.encode(item.getPassword()));
    }
    if (item.getRole() != null && !item.getRole().isEmpty()) {
        Set<Role> roles = item.getRole().stream()
            .map(this::getRoleByName)
            .collect(Collectors.toSet());
        existingItem.setRoles(roles);
    }

    return new ResponseEntity<>(repository.save(existingItem), HttpStatus.OK);
}

private Role getRoleByName(String roleName) {
    return switch (roleName.toLowerCase()) {
        case "admin" -> roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role ADMIN not found."));
        case "moderateur" -> roleRepository.findByName(ERole.ROLE_MODERATEUR)
                .orElseThrow(() -> new RuntimeException("Error: Role MODERATEUR not found."));
        case "journalist" -> roleRepository.findByName(ERole.ROLE_JOURNALIST)
                .orElseThrow(() -> new RuntimeException("Error: Role JOURNALIST not found."));
        case "collecteur" -> roleRepository.findByName(ERole.ROLE_COLLECTEUR)
                .orElseThrow(() -> new RuntimeException("Error: Role COLLECTEUR not found."));
        case "user" -> roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role USER not found."));
        default -> throw new RuntimeException("Error: Invalid role name - " + roleName);
    };
}


    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(repository.existsById(id), HttpStatus.OK);
    }
}