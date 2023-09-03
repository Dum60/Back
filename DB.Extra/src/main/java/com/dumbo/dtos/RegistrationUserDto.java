package com.dumbo.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.dumbo.validation.PasswordMatches;

@Getter
@Setter
@NoArgsConstructor
@PasswordMatches
public class RegistrationUserDto {
    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @NotEmpty
    private String password;
    private String passwordConfirm;
}
