package com.auth.jwtserver.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {
    
	@NotBlank
    @Size(min = 3, max = 30)
    private String username;
    
	@NotBlank
    @Size(max = 60)
    @Email
    private String email;
    
	@NotBlank
    @Size(min = 6, max = 60)
    private String password;
	
    private List<String> roles;
}
