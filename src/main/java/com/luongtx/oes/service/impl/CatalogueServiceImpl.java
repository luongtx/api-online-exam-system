package com.luongtx.oes.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.luongtx.oes.dto.CategoryDTO;
import com.luongtx.oes.entity.Category;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.exception.ApplicationUserException;
import com.luongtx.oes.repository.CategoryRepo;
import com.luongtx.oes.repository.QuestionRepo;
import com.luongtx.oes.service.CatalogueService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CatalogueServiceImpl implements CatalogueService {
	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	QuestionRepo questionRepo;

	@Override
	public List<CategoryDTO> findAll() {
		return categoryRepo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public Page<CategoryDTO> findAll(Pageable pageable, String searchKey) {
		Page<Category> categoryPage = categoryRepo.findAll(pageable, searchKey);
		return categoryPage.map(this::convertToDTO);
	}

	@Override
	public CategoryDTO findById(Long categoryId) {
		Category category = categoryRepo.getById(categoryId);
		return convertToDTO(category);
	}

	@Override
	public void save(CategoryDTO categoryDTO) {
		Category category = convertToEntity(categoryDTO);
		categoryRepo.save(category);
	}

	@Override
	public Page<Question> findAllQuestions(Long categoryId, Pageable pageable, String searchKey) {
		return questionRepo.findAllByCategory(categoryId, pageable, searchKey);
	}

	@Override
	public void saveQuestion(Long categoryId, Long questionId) {
		Category category = categoryRepo.getById(categoryId);
		Question question = questionRepo.getById(questionId);
		question.setCategory(category);
		questionRepo.save(question);
	}

	@Override
	public void saveQuestion(Long categoryId, Question question) {
		Category category = categoryRepo.getById(categoryId);
		question.setCategory(category);
		questionRepo.save(question);
	}

	@Override
	public void saveQuestions(Long categoryId, List<Question> questions) {
		Category category = categoryRepo.getById(categoryId);
		category.setQuestions(questions);
		categoryRepo.save(category);
	}

	@Override
	public void removeQuestion(Long questionId) {
		Question question = questionRepo.getById(questionId);
		question.setCategory(null);
		questionRepo.save(question);
	}

	@Override
	public void delete(Long id, boolean cascade) {
		try {
			categoryRepo.deleteCategory(id, cascade);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApplicationUserException("Cannot delete category with id: " + id);
		}
	}

	CategoryDTO convertToDTO(Category category) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());
		if (category.getCategoryParent() != null) {
			categoryDTO.setParentId(category.getCategoryParent().getId());
		}
		long numberOfQuestions = questionRepo.countQuestionsByCategoryId(category.getId());
		log.info(String.valueOf(numberOfQuestions));
		categoryDTO.setNumberOfQuestions(numberOfQuestions);
		return categoryDTO;
	}

	Category convertToEntity(CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setId(categoryDTO.getId());
		category.setName(categoryDTO.getName());
		if (categoryDTO.getParentId() != null) {
			Category categoryParent = categoryRepo.getById(categoryDTO.getParentId());
			category.setCategoryParent(categoryParent);
		}
		return category;
	}

}
