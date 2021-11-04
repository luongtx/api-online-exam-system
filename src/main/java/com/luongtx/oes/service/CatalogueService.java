package com.luongtx.oes.service;

import com.luongtx.oes.dto.CategoryDTO;
import com.luongtx.oes.entity.Question;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CatalogueService {
    List<CategoryDTO> findAll();

    Page<CategoryDTO> findAll(Pageable pageable, String searchKey);

    CategoryDTO findById(Long categoryId);

    void save(CategoryDTO categoryDTO);

    Page<Question> findAllQuestions(Long categoryId, Pageable pageable, String searchKey);

    void saveQuestion(Long categoryId, Long questionId);
    
    void saveQuestion(Long categoryId, Question question);

    void saveQuestions(Long categoryId, List<Question> questions);

    void removeQuestion(Long questionId);

    void delete(Long id, boolean cascade);
}
