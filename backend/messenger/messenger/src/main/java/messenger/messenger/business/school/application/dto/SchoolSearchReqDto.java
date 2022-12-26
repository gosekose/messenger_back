package messenger.messenger.business.school.application.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import messenger.messenger.business.common.BaseDefaultPageable;

@Data
@NoArgsConstructor
public class SchoolSearchReqDto extends BaseDefaultPageable {

    private String schoolName;
    private String schoolAddress;

    @Builder
    public SchoolSearchReqDto(String schoolName, String schoolAddress, Integer page, Integer size) {
        super(page, size);
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
    }

}
