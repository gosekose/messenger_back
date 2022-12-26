package messenger.messenger.business.school.presentation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import messenger.messenger.business.school.domain.School;

@Data
@NoArgsConstructor
public class SchoolResponseDto {
    Long schoolId;
    String schoolName;
    String schoolAddress;

    public SchoolResponseDto(School school) {
        this.schoolId = school.getId();
        this.schoolName = school.getSchoolName();
        this.schoolAddress = school.getSchoolAddress();
    }

}
