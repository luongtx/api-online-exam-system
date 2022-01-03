package com.luongtx.oes.service;

import com.luongtx.oes.dto.QuestionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    // void updateCatalogQuestion(QuestionDTO questionDTO);
    void save(QuestionDTO questionDTO);

    Page<QuestionDTO> findAll(Pageable pageable, String searchKey);

    Page<QuestionDTO> findAllByCatalog(Pageable pageable, String searchKey, Long catalogId);

    Page<QuestionDTO> findAllByExam(Pageable pageable, String searchKey, Long examId);

    Page<QuestionDTO> findAllExcludeCatalog(Pageable pageable, String searchKey, Long catalogId);

    Page<QuestionDTO> findAllExcludeExam(Pageable pageable, String searchKey, Long examId);

    void deleteQuestion(Long questionId);

    void deleteQuestion(Long questionId, Long catalogId, Long examId);
}
