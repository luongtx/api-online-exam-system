package com.luongtx.oes.service;

import com.luongtx.oes.dto.CatalogDTO;
import com.luongtx.oes.entity.Question;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CatalogService {
    List<CatalogDTO> findAll();

    Page<CatalogDTO> findAll(Pageable pageable, String searchKey);

    CatalogDTO findById(Long categoryId);

    void save(CatalogDTO catalogDTO);

    Page<Question> findAllQuestions(Long catalogId, Pageable pageable, String searchKey);

    void saveQuestion(Long catalogId, Long questionId);
    
    void saveQuestion(Long catalogId, Question question);

    void saveQuestions(Long catalogId, List<Long> questionIds);

    void removeQuestion(Long questionId);

    void delete(Long id, boolean cascade);
}
