package com.luongtx.oes.service;

import com.luongtx.oes.dto.ExamDTO;
import com.luongtx.oes.dto.ExamResultDTO;
import com.luongtx.oes.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExamService {

    List<ExamDTO> findAll();

    Page<ExamDTO> findAll(Pageable pageable, String keyword);

    List<Question> findAllQuestions(Long examId);

    Page<Question> findAllQuestions(Long examId, Pageable pageable);

    ExamDTO findDetailById(Long id);

    ExamResultDTO evaluateResult(String userToken, Long examId, List<List<Long>> listAnswers);

    List<ExamResultDTO> getRecentUserExams(String userToken);

    void save(ExamDTO examDTO);

    void save(ExamDTO examDTO, MultipartFile file);

    Long uploadBanner(MultipartFile file, Long examId);

    void delete(Long id);

    void saveQuestions(List<Question> questions, Long examId);

    void saveQuestion(Question question, Long examId);

    void deleteQuestion(Long questionId);
}
