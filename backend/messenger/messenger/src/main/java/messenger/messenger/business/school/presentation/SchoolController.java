package messenger.messenger.business.school.presentation;

import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messenger.messenger.business.common.RestPage;
import messenger.messenger.business.school.application.SchoolService;
import messenger.messenger.business.school.application.dto.SchoolSaveDto;
import messenger.messenger.business.school.application.dto.SchoolSearchReqDto;
import messenger.messenger.business.school.domain.School;
import messenger.messenger.business.school.infra.repository.query.dto.SchoolSearchDto;
import messenger.messenger.business.school.presentation.dto.SchoolResponseDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/v1/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping("/search")
    public ResponseEntity getSchoolsSearchRes(@RequestBody SchoolSearchReqDto reqDto) {
        Page<SchoolSearchDto> schoolSearchDto = schoolService.getSchoolSearchDto(reqDto);
        return new ResponseEntity(new RestPage<>(schoolSearchDto), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity insertSchool(@Valid @RequestBody SchoolSaveDto saveDto,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Long savedId = schoolService.save(saveDto);
        return new ResponseEntity(savedId,  HttpStatus.CREATED);
    }

    @GetMapping("/{schoolId}")
    public ResponseEntity getSchoolOne(@PathVariable("schoolId") Long schoolId) {
        School school = schoolService.findOne(schoolId);
        return new ResponseEntity(new SchoolResponseDto(school), HttpStatus.OK);
    }

}
