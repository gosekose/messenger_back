package messenger.messenger.business.school.application;

import messenger.messenger.business.school.application.dto.SchoolSaveDto;
import messenger.messenger.business.school.application.dto.SchoolSearchReqDto;
import messenger.messenger.business.school.domain.School;
import messenger.messenger.business.school.infra.repository.SchoolQueryRepositoryImpl;
import messenger.messenger.business.school.infra.repository.SchoolRepository;
import messenger.messenger.business.school.infra.repository.query.dto.SchoolSearchDto;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class SchoolServiceTest {

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    SchoolService schoolService;

    @BeforeEach
    public void dataInit() {

        // 학교 추가
        String[] schoolNames = {"서울과학기술대학교", "서울대학교", "연세대학교"};
        String[] schoolAddress = {"서울 공릉동", "서울 관악구", "서울 서대문구"};

        for (int i=0; i<schoolNames.length; i++) {
            schoolRepository.save(
                    School.builder()
                            .schoolName(schoolNames[i])
                            .schoolAddress(schoolAddress[i])
                            .schoolRegistrationNum(String.valueOf(i))
                            .build());
        }
    }

    /**
     *
     * 학교 저장 및 학교 find
     *
     * @throws Exception
     */
    @Test
    public void 학교Dto_저장() throws Exception {
        //given

        Long savedId = schoolService.save(
                SchoolSaveDto.builder()
                        .schoolName("고려대학교")
                        .schoolAddress("서울 성북구")
                        .build());

        //when
        School school = schoolService.findOne(savedId);

        //then
        assertThat(school.getId()).isEqualTo(savedId);
    }

    @Test
    public void 학교_동적_검색_이름() throws Exception {
        //given
        SchoolSearchReqDto reqDto = SchoolSearchReqDto.builder()
                .schoolName("서울")
                .build();

        reqDto.updatePageable();

        //when
        Page<SchoolSearchDto> schoolSearchDto = schoolService.getSchoolSearchDto(reqDto);

        //then
        assertThat(schoolSearchDto.getSize()).isEqualTo(10);
        assertThat(schoolSearchDto.getTotalElements()).isEqualTo(2);
        assertThat(schoolSearchDto.getContent().get(0).getSchoolName()).isEqualTo("서울과학기술대학교");
    }

    @Test
    public void 학교_동적_검색_주소() throws Exception {
        //given
        SchoolSearchReqDto reqDto = SchoolSearchReqDto.builder()
                .schoolAddress("서울")
                .build();

        reqDto.updatePageable();

        //when
        Page<SchoolSearchDto> schoolSearchDto = schoolService.getSchoolSearchDto(reqDto);

        //then
        assertThat(schoolSearchDto.getSize()).isEqualTo(10);
        assertThat(schoolSearchDto.getTotalElements()).isEqualTo(3);
        assertThat(schoolSearchDto.getContent().get(0).getSchoolName()).isEqualTo("서울과학기술대학교");
    }

    @Test
    public void 학교_동적_검색_이름_주소() throws Exception {
        //given
        SchoolSearchReqDto reqDto = SchoolSearchReqDto.builder()
                .schoolName("서울")
                .schoolAddress("서울")
                .build();

        reqDto.updatePageable();

        //when
        Page<SchoolSearchDto> schoolSearchDto = schoolService.getSchoolSearchDto(reqDto);

        //then
        assertThat(schoolSearchDto.getSize()).isEqualTo(10);
        assertThat(schoolSearchDto.getTotalElements()).isEqualTo(2);
        assertThat(schoolSearchDto.getContent().get(0).getSchoolName()).isEqualTo("서울과학기술대학교");
    }

    @Test
    public void 학교_동적_검색_클라이언트_페이징() throws Exception {
        //given
        SchoolSearchReqDto reqDto = SchoolSearchReqDto.builder()
                .schoolName("서울")
                .page(0)
                .size(5)
                .build();

        reqDto.updatePageable();

        //when
        Page<SchoolSearchDto> schoolSearchDto = schoolService.getSchoolSearchDto(reqDto);

        //then
        assertThat(schoolSearchDto.getSize()).isEqualTo(5);
        assertThat(schoolSearchDto.getTotalElements()).isEqualTo(2);
        assertThat(schoolSearchDto.getContent().get(0).getSchoolName()).isEqualTo("서울과학기술대학교");
    }

    @Test
    public void 학교_동적_검색_클라이언트_페이징2() throws Exception {
        //given
        SchoolSearchReqDto reqDto = SchoolSearchReqDto.builder()
                .schoolName("서울")
                .page(1)
                .size(5)
                .build();

        reqDto.updatePageable();

        //when
        Page<SchoolSearchDto> schoolSearchDto = schoolService.getSchoolSearchDto(reqDto);

        //then
        assertThat(schoolSearchDto.getSize()).isEqualTo(5);
        assertThat(schoolSearchDto.getTotalElements()).isEqualTo(2);
    }


}