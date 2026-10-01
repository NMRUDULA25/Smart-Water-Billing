package com.smartwater.smartwaterbilling.dto;

import com.smartwater.smartwaterbilling.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String username;
    private String password;
    private Role role;
}