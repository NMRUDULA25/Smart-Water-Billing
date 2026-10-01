package com.smartwater.smartwaterbilling.mapper;

import com.smartwater.smartwaterbilling.dto.UserDTO;
import com.smartwater.smartwaterbilling.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {

        if(user == null){
            return null;
        }

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

    public static User toEntity(UserDTO dto){

        if(dto == null){
            return null;
        }

        User user = new User();

        user.setId(dto.getId());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        return user;
    }
}