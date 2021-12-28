package com.luongtx.oes.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.luongtx.oes.dto.CatalogDTO;
import com.luongtx.oes.dto.QuestionDTO;
import com.luongtx.oes.entity.Catalog;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.exception.ApplicationUserException;
import com.luongtx.oes.repository.CatalogRepo;
import com.luongtx.oes.repository.QuestionRepo;
import com.luongtx.oes.service.CatalogService;
import com.luongtx.oes.utils.converter.QuestionConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CatalogServiceImpl implements CatalogService {
	@Autowired
	CatalogRepo catalogRepo;

	@Autowired
	QuestionRepo questionRepo;

	@Override
	public List<CatalogDTO> findAll() {
		return catalogRepo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public Page<CatalogDTO> findAll(Pageable pageable, String searchKey) {
		Page<Catalog> categoryPage = catalogRepo.findAll(pageable, searchKey);
		return categoryPage.map(this::convertToDTO);
	}

	@Override
	public CatalogDTO findById(Long categoryId) {
		Catalog catalog = catalogRepo.getById(categoryId);
		return convertToDTO(catalog);
	}

	@Override
	public void save(CatalogDTO catalogDTO) {
		Catalog catalog = convertToEntity(catalogDTO);
		catalogRepo.save(catalog);
	}

	@Override
	public Page<QuestionDTO> findAllQuestions(Long catalogId, Pageable pageable, String searchKey) {
		return questionRepo.findAllByCatalog(catalogId, pageable, searchKey)
				.map(QuestionConverter::convertEntityToDTO);
	}

	@Override
	public void saveQuestion(Long catalogId, Long questionId) {
		Catalog catalog = catalogRepo.getById(catalogId);
		Question question = questionRepo.getById(questionId);
		question.setCatalog(catalog);
		questionRepo.save(question);
	}

	@Override
	public void saveQuestions(Long catalogId, List<Long> questionIds) {
		Catalog catalog = catalogRepo.getById(catalogId);
		log.debug(catalog);
		List<Question> questions = questionRepo.findAllByIdIn(questionIds);
		log.debug(questions);
		questions.forEach(question -> {
			question.setCatalog(catalog);
		});
		questionRepo.saveAll(questions);
	}

	@Override
	public void removeQuestion(Long questionId) {
		Question question = questionRepo.getById(questionId);
		question.setCatalog(null);
		questionRepo.save(question);
	}

	@Override
	public void delete(Long id, boolean cascade) {
		try {
			catalogRepo.deleteCategory(id, cascade);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApplicationUserException("Cannot delete category with id: " + id);
		}
	}

	CatalogDTO convertToDTO(Catalog catalog) {
		CatalogDTO catalogDTO = new CatalogDTO();
		catalogDTO.setId(catalog.getId());
		catalogDTO.setName(catalog.getName());
		if (catalog.getCatalogParent() != null) {
			catalogDTO.setParentId(catalog.getCatalogParent().getId());
		}
		long numberOfQuestions = questionRepo.countQuestionsByCatalogId(catalog.getId());
		log.info(String.valueOf(numberOfQuestions));
		catalogDTO.setNumberOfQuestions(numberOfQuestions);
		return catalogDTO;
	}

	Catalog convertToEntity(CatalogDTO catalogDTO) {
		Catalog catalog = new Catalog();
		catalog.setId(catalogDTO.getId());
		catalog.setName(catalogDTO.getName());
		if (catalogDTO.getParentId() != null) {
			Catalog catalogParent = catalogRepo.getById(catalogDTO.getParentId());
			catalog.setCatalogParent(catalogParent);
		}
		return catalog;
	}

}
