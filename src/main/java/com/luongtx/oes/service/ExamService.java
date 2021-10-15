package com.luongtx.oes.service;

import com.luongtx.oes.dto.ExamResultDTO;
import com.luongtx.oes.entity.Exam;
import com.luongtx.oes.entity.Question;

import java.util.List;

public interface ExamService extends BaseService<Exam> {
    List<Question> findQuestionsByExamId(Long id);

    Exam findDetailById(Long id);

    ExamResultDTO evaluateResult(Long examId, List<List<Long>> listAnswers);
}
