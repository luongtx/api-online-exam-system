package com.luongtx.oes.service.impl;

import com.luongtx.oes.dto.QuestionDTO;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.repository.QuestionRepo;
import com.luongtx.oes.service.QuestionService;
import com.luongtx.oes.utils.converter.QuestionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepo questionRepo;

    @Override
    public void save(QuestionDTO questionDTO) {

    }

    @Override
    public Page<QuestionDTO> findAll(Pageable pageable, String searchKey) {
        return questionRepo.findAll(pageable, searchKey)
                .map(QuestionConverter::convertEntityToDTO);
    }

    @Override
    public Page<QuestionDTO> findAllExcluded(Pageable pageable, String searchKey, Long catalogId) {
        return questionRepo.findAllExceptCatalog(pageable, searchKey, catalogId)
                .map(QuestionConverter:: convertEntityToDTO);
    }
}
