package com.luongtx.oes.web;

import com.luongtx.oes.dto.ExamResultDTO;
import com.luongtx.oes.entity.Exam;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Exam> getExamDetailById(@PathVariable(name = "id") Long id) {
        Exam exam = examService.findDetailById(id);
        return new ResponseEntity<>(exam, HttpStatus.OK);
    }

    @GetMapping(path = {"/{id}/questions", "/{id}/start"})
    public ResponseEntity<List<Question>> getQuestionsByExamId(@PathVariable(name = "id") Long id) {
        List<Question> questions = examService.findQuestionsByExamId(id);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/submit")
    public ResponseEntity<ExamResultDTO> evaluateExamResult(@PathVariable("id") Long examId, @RequestBody List<List<Long>> listAnswers) {
        ExamResultDTO examResultDTO = examService.evaluateResult(examId, listAnswers);
        return new ResponseEntity<>(examResultDTO, HttpStatus.OK);
    }
}
