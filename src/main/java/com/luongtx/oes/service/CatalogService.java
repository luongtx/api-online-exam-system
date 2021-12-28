package com.luongtx.oes.service;

import java.util.List;

import com.luongtx.oes.dto.CatalogDTO;
import com.luongtx.oes.dto.QuestionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CatalogService {
    List<CatalogDTO> findAll();

    Page<CatalogDTO> findAll(Pageable pageable, String searchKey);

    CatalogDTO findById(Long categoryId);

    void save(CatalogDTO catalogDTO);

    Page<QuestionDTO> findAllQuestions(Long catalogId, Pageable pageable, String searchKey);

    void saveQuestion(Long catalogId, Long questionId);
    
    void saveQuestions(Long catalogId, List<Long> questionIds);

    void removeQuestion(Long questionId);

    void delete(Long id, boolean cascade);
}
