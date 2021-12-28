package com.luongtx.oes.service;

import java.util.List;

import com.luongtx.oes.dto.ExamDTO;
import com.luongtx.oes.dto.ExamResultDTO;
import com.luongtx.oes.dto.QuestionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ExamService {

    List<ExamDTO> findAll();

    Page<ExamDTO> findAll(Pageable pageable, String searchKey);

    Page<QuestionDTO> findAllQuestions(Long examId, Pageable pageable);

    ExamDTO findDetailById(Long id);

    ExamResultDTO evaluateResult(String userToken, Long examId, List<List<Long>> listAnswers);

    List<ExamResultDTO> getRecentUserExams(String userToken);

    void save(ExamDTO examDTO);

    void save(ExamDTO examDTO, MultipartFile file);

    Long uploadBanner(MultipartFile file, Long examId);

    void delete(Long id);

    // void saveQuestions(List<Question> questions, Long examId);

    void saveQuestion(QuestionDTO questionDTO, Long examId);

    void deleteQuestion(Long questionId);
}
