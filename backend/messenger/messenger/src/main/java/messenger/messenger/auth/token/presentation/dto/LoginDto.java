package messenger.messenger.auth.token.presentation.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDto {

    @NotNull
    private String email;

    @NotNull
    private String password;

}
