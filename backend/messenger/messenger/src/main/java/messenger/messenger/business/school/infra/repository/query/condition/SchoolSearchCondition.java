package messenger.messenger.business.school.infra.repository.query.condition;

import lombok.Builder;
import lombok.Data;

@Data
public class SchoolSearchCondition {

    private String schoolName;
    private String schoolAddress;

    @Builder
    public SchoolSearchCondition(String schoolName, String schoolAddress) {
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
    }

    public static SchoolSearchCondition of (String schoolName, String schoolAddress) {
        return new SchoolSearchCondition(schoolName, schoolAddress);
    }

}
