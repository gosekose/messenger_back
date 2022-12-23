package messenger.messenger.business.school.infra.repository.query.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class SchoolSearchDto {

    private Long id;
    private String schoolName;
    private String schoolAddress;

    @Builder
    @QueryProjection
    public SchoolSearchDto(Long id, String schoolName, String schoolAddress) {
        this.id = id;
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
    }
}
