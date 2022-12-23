package messenger.messenger.auth.user.application.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class FormLoginUserDto {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @Builder
    public FormLoginUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
