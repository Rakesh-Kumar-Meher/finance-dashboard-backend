package finance_dasboard.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import finance_dasboard.dto.UserRequestDTO;
import finance_dasboard.dto.UserResponseDTO;
import finance_dasboard.entity.RoleBasedAccess;
import finance_dasboard.service.UserService;
import finance_dasboard.util.RoleChecker;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/users")
public class UserController{
    
    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping
    public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO dto, @RequestHeader("role") RoleBasedAccess role){
        RoleChecker.checkAdmin(role);
        return userService.createUser(dto);
    }


    @GetMapping
    public List<UserResponseDTO> getAllUser(@RequestHeader("role") RoleBasedAccess role) {
        RoleChecker.checkAdmin(role);

        return userService.getAllUsers();
    }


    @PutMapping("/{id}/status")
    public UserResponseDTO updateStatus(@PathVariable Long id, @RequestParam boolean active, @RequestHeader("role") RoleBasedAccess role) {
        
        RoleChecker.checkAdmin(role);
        
        return userService.updateUserStatus(id, active);
    }

    @PutMapping("/{id}/role")
    public UserResponseDTO updateRole(@PathVariable Long id, @RequestParam RoleBasedAccess newRole, @RequestHeader("role") RoleBasedAccess role){
        RoleChecker.checkAdmin(role);
        return userService.updateUserRole(id, newRole);
    }
    
}
