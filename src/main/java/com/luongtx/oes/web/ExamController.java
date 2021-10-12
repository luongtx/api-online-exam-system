package com.luongtx.oes.web;

import com.luongtx.oes.dto.ExamResultDTO;
import com.luongtx.oes.entity.Exam;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
@CrossOrigin
public class ExamController {

    @Autowired
    ExamService examService;

    @GetMapping(path = "")
    public List<Exam> getAllExams() {
        return examService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Exam getExamDetailById(@PathVariable(name = "id") Integer id) {
        return examService.findDetailById(id);
    }

    @GetMapping(path = {"/{id}/questions", "/{id}/start"})
    public List<Question> getQuestionsByExamId(@PathVariable(name = "id") Integer id) {
        return examService.findQuestionsByExamId(id);
    }

    @PostMapping(path = "/{id}/submit")
    public ExamResultDTO evaluateExamResult(@PathVariable("id") Integer examId, @RequestBody List<List<Integer>> listAnswers) {
        return examService.evaluateResult(examId, listAnswers);
    }
}
