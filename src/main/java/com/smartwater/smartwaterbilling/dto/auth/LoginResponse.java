package com.smartwater.smartwaterbilling.dto.auth;

import com.smartwater.smartwaterbilling.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String username;
    private Role role;
}