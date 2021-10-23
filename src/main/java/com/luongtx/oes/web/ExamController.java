package com.luongtx.oes.web;

import com.luongtx.oes.constants.PageConstants;
import com.luongtx.oes.constants.RoleConstants;
import com.luongtx.oes.dto.ExamDTO;
import com.luongtx.oes.dto.ExamResultDTO;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exams")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ExamController {

    @Autowired
    ExamService examService;

    @GetMapping(path = "")
    public ResponseEntity<Map<String, Object>> getAllExams(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size
    ) {
        Map<String, Object> response = new HashMap<>();
        if (page == null || size == null) {
            List<ExamDTO> examDTOS = examService.findAll();
            response.put(PageConstants.DATA, examDTOS);
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<ExamDTO> pageExamDTOs = examService.findAll(pageable);
            response.put(PageConstants.DATA, pageExamDTOs.getContent());
//            response.put(PageConstants.TOTAL_ITEM, pageExamDTOs.getTotalElements());
            response.put(PageConstants.TOTAL_PAGE, pageExamDTOs.getTotalPages());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ExamDTO> getExamDetailById(@PathVariable(name = "id") Long id) {
        ExamDTO examDTO = examService.findDetailById(id);
        return new ResponseEntity<>(examDTO, HttpStatus.OK);
    }

    @GetMapping(path = {"/{id}/questions", "/{id}/start"})
    public ResponseEntity<Map<String, Object>> getQuestionsByExamId(
            @PathVariable(name = "id") Long examId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        Map<String, Object> response = new HashMap<>();
        if (page == null || size == null) {
            List<Question> questions = examService.findAllQuestions(examId);
            response.put(PageConstants.DATA, questions);
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<Question> pageQuestions = examService.findAllQuestions(examId, pageable);
            response.put(PageConstants.DATA, pageQuestions.getContent());
//            response.put(PageConstants.TOTAL_ITEM, pageQuestions.getTotalElements());
            response.put(PageConstants.TOTAL_PAGE, pageQuestions.getTotalPages());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/submit")
    public ResponseEntity<ExamResultDTO> evaluateExamResult(@RequestHeader("Authorization") String userToken,
                                                            @PathVariable("id") Long examId,
                                                            @RequestBody List<List<Long>> listAnswers) {
        ExamResultDTO examResultDTO = examService.evaluateResult(userToken, examId, listAnswers);
        return new ResponseEntity<>(examResultDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/recent")
    public ResponseEntity<List<ExamResultDTO>> getRecentUserExams(@RequestHeader("Authorization") String userToken) {
        List<ExamResultDTO> examResultDTOS = examService.getRecentUserExams(userToken);
        return new ResponseEntity<>(examResultDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    @Secured(RoleConstants.ROLE_ADMIN)
    public void saveExam(@RequestBody @Valid ExamDTO examDTO) {
        examService.save(examDTO);
    }

    @PostMapping(value = "/upload")
    @Secured(RoleConstants.ROLE_ADMIN)
    public Long uploadBanner(@RequestBody MultipartFile file,
                             @RequestParam(name = "id", required = false) Long examId) {
        return examService.uploadBanner(file, examId);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Secured(RoleConstants.ROLE_ADMIN)
    public void deleteExam(@PathVariable(name = "id") Long id) {
        examService.delete(id);
    }

    @PostMapping(value = "/{id}/questions/save")
    @Secured(RoleConstants.ROLE_ADMIN)
    public void saveQuestions(@RequestBody List<Question> questions, @PathVariable("id") Long examId) {
        examService.saveQuestions(questions, examId);
    }

    @PostMapping(value = "/{id}/question/save")
    @Secured(RoleConstants.ROLE_ADMIN)
    public void saveQuestion(@RequestBody Question question, @PathVariable("id") Long examId) {
        examService.saveQuestion(question, examId);
    }

    @DeleteMapping(value = "/question/{id}/delete")
    @Secured(RoleConstants.ROLE_ADMIN)
    public void deleteQuestion(@PathVariable("id") Long questionId) {
        examService.deleteQuestion(questionId);
    }
}
