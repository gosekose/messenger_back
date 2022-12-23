package messenger.messenger.business.school.infra.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import messenger.messenger.business.school.domain.QSchool;
import messenger.messenger.business.school.infra.repository.query.condition.SchoolSearchCondition;
import messenger.messenger.business.school.infra.repository.query.dto.QSchoolSearchDto;
import messenger.messenger.business.school.infra.repository.query.dto.SchoolSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static messenger.messenger.business.school.domain.QSchool.*;

@Repository
@RequiredArgsConstructor
public class SchoolQueryRepositoryImpl implements SchoolQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<SchoolSearchDto> searchSchoolsPageComplex(SchoolSearchCondition condition, Pageable pageable) {

        List<SchoolSearchDto> content = query
                .select(
                        new QSchoolSearchDto(
                                school.id,
                                school.schoolName,
                                school.schoolAddress))
                .from(school)
                .where(
                        schoolNameContains(condition.getSchoolName()),
                        schoolAddressContains(condition.getSchoolAddress())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(school.count())
                .from(school)
                .where(
                        schoolNameContains(condition.getSchoolName()),
                        schoolAddressContains(condition.getSchoolAddress())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression schoolAddressContains(String schoolAddress) {
        return schoolAddress != null ? school.schoolAddress.contains(schoolAddress) : null;
    }

    private BooleanExpression schoolNameContains(String schoolName) {
        return schoolName != null ? school.schoolName.contains(schoolName) : null;

    }
}
