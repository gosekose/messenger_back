package messenger.messenger.business.school.infra.repository;

import messenger.messenger.business.school.infra.repository.query.condition.SchoolSearchCondition;
import messenger.messenger.business.school.infra.repository.query.dto.SchoolSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolQueryRepository {

    Page<SchoolSearchDto> searchSchoolsPageComplex(SchoolSearchCondition condition, Pageable pageable);
}
