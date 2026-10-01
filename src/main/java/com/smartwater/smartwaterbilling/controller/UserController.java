package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.UserDTO;
import com.smartwater.smartwaterbilling.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO dto){
        return service.createUser(dto);
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id){
        return service.getUserById(id);
    }

    @GetMapping
    public List<UserDTO> getAllUsers(){
        return service.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id,
                              @RequestBody UserDTO dto){
        return service.updateUser(id,dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        service.deleteUser(id);
    }
}