package com.example.backend.controllers.admin;

import com.example.backend.dtos.UserEditDto;
import com.example.backend.entites.Role;
import com.example.backend.services.RoleService;
import com.example.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminUserController{
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserEditDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserEditDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/getRoles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserEditDto userEditDto) {
        userService.updateUser(id, userEditDto);
        return ResponseEntity.ok("User updated successfully");
    }


}
