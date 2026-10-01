package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.dto.UserDTO;
import com.smartwater.smartwaterbilling.dto.auth.LoginRequest;
import com.smartwater.smartwaterbilling.dto.auth.LoginResponse;
import com.smartwater.smartwaterbilling.dto.auth.RegisterRequest;
import com.smartwater.smartwaterbilling.entity.User;
import com.smartwater.smartwaterbilling.repository.UserRepository;
import com.smartwater.smartwaterbilling.security.JwtService;
import com.smartwater.smartwaterbilling.service.UserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository repository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // ---------------- CREATE USER ----------------

    @Override
    public UserDTO createUser(UserDTO dto) {

        User user = new User();

        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        User savedUser = repository.save(user);

        return convertToDTO(savedUser);
    }

    // ---------------- GET USER ----------------

    @Override
    public UserDTO getUserById(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToDTO(user);
    }

    // ---------------- GET ALL USERS ----------------

    @Override
    public List<UserDTO> getAllUsers() {

        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ---------------- UPDATE USER ----------------

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {

        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        User updatedUser = repository.save(user);

        return convertToDTO(updatedUser);
    }

    // ---------------- DELETE USER ----------------

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    // ---------------- REGISTER ----------------

    @Override
    public void register(RegisterRequest request) {

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        repository.save(user);
    }

    // ---------------- LOGIN ----------------

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user.getUsername());

        return new LoginResponse(
                token,
                user.getUsername(),
                user.getRole()
        );
    }

    // ---------------- DTO CONVERSION ----------------

    private UserDTO convertToDTO(User user) {

        return new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}