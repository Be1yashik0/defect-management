package com.defect_management.server.dto;

import com.defect_management.server.entity.User.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private Role role;
}
