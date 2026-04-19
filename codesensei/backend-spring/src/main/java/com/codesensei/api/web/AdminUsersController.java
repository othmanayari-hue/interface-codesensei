package com.codesensei.api.web;

import com.codesensei.api.domain.User;
import com.codesensei.api.repo.UserRepository;
import com.codesensei.api.web.dto.UserDtos;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUsersController {
    private final UserRepository userRepo;

    public AdminUsersController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public Page<UserDtos.UserResponse> list(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size) {
        return userRepo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(UserDtos.UserResponse::from);
    }

    @PutMapping("/{id}")
    public UserDtos.UserResponse update(@PathVariable long id, @Valid @RequestBody UserDtos.UpdateUserRequest req) {
        User u = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        u.setNom(req.nom());
        u.setPrenom(req.prenom());
        u.setDateNaissance(req.dateNaissance());
        return UserDtos.UserResponse.from(userRepo.save(u));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        if (!userRepo.existsById(id)) throw new IllegalArgumentException("User not found");
        userRepo.deleteById(id);
    }
}

