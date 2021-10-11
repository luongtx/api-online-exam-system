package com.luongtx.oes.web;

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
    public Exam getExamById(@PathVariable(name = "id") Integer id) {
        return examService.findById(id);
    }

    @GetMapping(path = {"/{id}/questions", "/{id}/start"})
    public List<Question> getQuestionsByExamId(@PathVariable(name = "id") Integer id) {
        return examService.findQuestionsByExamId(id);
    }
}
