package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    @NotEmpty
    private String name;

    @NotEmpty
    @Size(min = 5, max = 15, message = "Username must contain 7 - 15 characters")
    private String username;

    @NotEmpty
    @Size(min = 5, max = 15, message = "Password must contain 7 - 15 characters")
    private String password;

    @NotEmpty
    @Email(message = "Email invalid")
    private String email;

}
