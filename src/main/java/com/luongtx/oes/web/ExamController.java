package com.luongtx.oes.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.luongtx.oes.constants.PageConstants;
import com.luongtx.oes.constants.RoleConstants;
import com.luongtx.oes.dto.ExamDTO;
import com.luongtx.oes.dto.ExamResultDTO;
import com.luongtx.oes.dto.QuestionDTO;
import com.luongtx.oes.service.ExamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/exams")
public class ExamController {

    @Autowired
    ExamService examService;

    @GetMapping(path = "")
    public ResponseEntity<Map<String, Object>> getAllExams(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sortKey,
            @RequestParam(name = "search", required = false, defaultValue = "") String searchKey) {
        Map<String, Object> response = new HashMap<>();
        if (page == null || size == null) {
            List<ExamDTO> examDTOS = examService.findAll();
            response.put(PageConstants.DATA, examDTOS);
        } else {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortKey));
            Page<ExamDTO> pageExamDTOs = examService.findAll(pageable, searchKey);
            response.put(PageConstants.DATA, pageExamDTOs.getContent());
            response.put(PageConstants.TOTAL_PAGE, pageExamDTOs.getTotalPages());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ExamDTO> getExamDetailById(@PathVariable(name = "id") Long id) {
        ExamDTO examDTO = examService.findDetailById(id);
        return new ResponseEntity<>(examDTO, HttpStatus.OK);
    }

    @GetMapping(path = { "/{id}/questions", "/{id}/start" })
    public ResponseEntity<Map<String, Object>> getQuestionsByExamId(
            @PathVariable(name = "id") Long examId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10000") Integer size) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionDTO> qPage = examService.findAllQuestions(examId, pageable);
        response.put(PageConstants.DATA, qPage.getContent());
        response.put(PageConstants.TOTAL_PAGE, qPage.getTotalPages());
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

    // @PostMapping(value = "/{id}/questions/save")
    // @Secured(RoleConstants.ROLE_ADMIN)
    // public void saveQuestions(@RequestBody List<Question> questions,
    // @PathVariable("id") Long examId) {
    // examService.saveQuestions(questions, examId);
    // }

    @PostMapping(value = "/{id}/question/save")
    @Secured(RoleConstants.ROLE_ADMIN)
    public void saveQuestion(@RequestBody QuestionDTO questionDTO, @PathVariable("id") Long examId) {
        examService.saveQuestion(questionDTO, examId);
    }

    @DeleteMapping(value = "/question/{id}/delete")
    @Secured(RoleConstants.ROLE_ADMIN)
    public void deleteQuestion(@PathVariable("id") Long questionId) {
        examService.deleteQuestion(questionId);
    }
}
