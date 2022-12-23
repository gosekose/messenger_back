package messenger.messenger.business.school.application.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SchoolSaveDto {

    @NotNull
    private String schoolName;

    @NotNull
    private String schoolAddress;

    @NotNull
    private String schoolRegistrationNum;

    @Builder
    public SchoolSaveDto(String schoolName, String schoolAddress, String schoolRegistrationNum) {
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
        this.schoolRegistrationNum = schoolRegistrationNum;
    }
}
