package com.luongtx.oes.web;

import com.luongtx.oes.constants.RoleConstants;
import com.luongtx.oes.dto.ExamDTO;
import com.luongtx.oes.dto.ExamResultDTO;
import com.luongtx.oes.entity.Exam;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/exams")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ExamController {

    @Autowired
    ExamService examService;

    @GetMapping(path = "")
    public ResponseEntity<List<Exam>> getAllExams() {
        List<Exam> exams = examService.findAll();
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ExamDTO> getExamDetailById(@PathVariable(name = "id") Long id) {
        ExamDTO examDTO = examService.findDetailById(id);
        return new ResponseEntity<>(examDTO, HttpStatus.OK);
    }

    @GetMapping(path = {"/{id}/questions", "/{id}/start"})
    public ResponseEntity<List<Question>> getQuestionsByExamId(@PathVariable(name = "id") Long id) {
        List<Question> questions = examService.findQuestionsByExamId(id);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/submit")
    public ResponseEntity<ExamResultDTO> evaluateExamResult(@RequestHeader("Authorization") String userToken,
                                                            @PathVariable("id") Long examId,
                                                            @RequestBody List<List<Long>> listAnswers) {
        ExamResultDTO examResultDTO = examService.evaluateResult(userToken, examId, listAnswers);
        return new ResponseEntity<>(examResultDTO, HttpStatus.OK);
    }

    @GetMapping(path = "recent")
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

    @PostMapping(value = "{id}/questions/save")
    @Secured(RoleConstants.ROLE_ADMIN)
    public void saveQuestions(@RequestBody List<Question> questions, @PathVariable("id") Long examId) {
        examService.saveQuestions(questions, examId);
    }
}
