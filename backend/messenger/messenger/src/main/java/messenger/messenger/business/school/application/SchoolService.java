package messenger.messenger.business.school.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messenger.messenger.business.school.application.dto.SchoolSaveDto;
import messenger.messenger.business.school.application.dto.SchoolSearchReqDto;
import messenger.messenger.business.school.domain.School;
import messenger.messenger.business.school.infra.repository.SchoolQueryRepositoryImpl;
import messenger.messenger.business.school.infra.repository.SchoolRepository;
import messenger.messenger.business.school.infra.repository.query.condition.SchoolSearchCondition;
import messenger.messenger.business.school.infra.repository.query.dto.SchoolSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolQueryRepositoryImpl schoolQueryRepository;
    private final SchoolRepository schoolRepository;


    public School findOne(Long id) {
        Optional<School> optional = schoolRepository.findById(id);

        return optional.orElseThrow(
                () -> {throw new IllegalArgumentException("존재하지 않는 학교입니다.");});
    };

    @Transactional
    public Long save(SchoolSaveDto schoolSaveDto) {

        School school = schoolRepository.save(
                School.builder()
                        .schoolName(schoolSaveDto.getSchoolName())
                        .schoolAddress(schoolSaveDto.getSchoolAddress())
                        .schoolRegistrationNum(schoolSaveDto.getSchoolRegistrationNum())
                        .build()
        );
        return school.getId();
    }


    /**
     *
     * 학교 검색
     * schoolSearchReqDto를 입력받아, name, address로 동적 쿼리 생성
     * schoolSearchReqDto의 page 관련 파라미터를 받아 pageable 생성
     *
     * @param schoolSearchReqDto
     * @return
     */
    public Page<SchoolSearchDto> getSchoolSearchDto(SchoolSearchReqDto schoolSearchReqDto) {

        SchoolSearchCondition condition = SchoolSearchCondition.of(schoolSearchReqDto.getSchoolName(), schoolSearchReqDto.getSchoolAddress());
        Pageable pageable = PageRequest.of(schoolSearchReqDto.getPage(), schoolSearchReqDto.getSize(), Sort.by("id"));

        return schoolQueryRepository.searchSchoolsPageComplex(condition, pageable);

    }



}
