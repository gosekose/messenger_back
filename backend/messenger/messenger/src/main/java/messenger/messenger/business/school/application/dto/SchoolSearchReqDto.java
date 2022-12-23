package messenger.messenger.business.school.application.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import messenger.messenger.business.school.infra.repository.query.dto.SchoolSearchDto;

@Data
@NoArgsConstructor
public class SchoolSearchReqDto {

    private String schoolName;
    private String schoolAddress;
    private Integer page;
    private Integer size;

    @Builder
    public SchoolSearchReqDto(String schoolName, String schoolAddress, Integer page, Integer size) {
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
        this.page = page;
        this.size = size;
    }

    public void updatePageable() {
        if (this.page == null) page = 0;
        if (this.size == null) size = 10;
    }
}
