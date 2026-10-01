package com.smartwater.smartwaterbilling.service;

import com.smartwater.smartwaterbilling.dto.UserDTO;
import com.smartwater.smartwaterbilling.dto.auth.LoginRequest;
import com.smartwater.smartwaterbilling.dto.auth.LoginResponse;
import com.smartwater.smartwaterbilling.dto.auth.RegisterRequest;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO dto);

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(Long id, UserDTO dto);

    void deleteUser(Long id);

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}