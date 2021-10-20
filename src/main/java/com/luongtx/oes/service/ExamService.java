package com.luongtx.oes.service;

import com.luongtx.oes.dto.ExamDTO;
import com.luongtx.oes.dto.ExamResultDTO;
import com.luongtx.oes.entity.Exam;
import com.luongtx.oes.entity.Question;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExamService {

    List<Exam> findAll();

    List<Question> findQuestionsByExamId(Long id);

    ExamDTO findDetailById(Long id);

    ExamResultDTO evaluateResult(String userToken, Long examId, List<List<Long>> listAnswers);

    List<ExamResultDTO> getRecentUserExams(String userToken);

    void save(ExamDTO examDTO);

    void save(ExamDTO examDTO, MultipartFile file);

    Long uploadBanner(MultipartFile file, Long examId);

    void delete(Long id);

    void saveQuestions(List<Question> questions, Long examId);
}
