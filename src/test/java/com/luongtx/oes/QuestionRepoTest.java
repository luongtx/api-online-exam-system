package com.luongtx.oes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import com.luongtx.oes.entity.Question;
import com.luongtx.oes.repository.QuestionRepo;
import com.luongtx.oes.repository.specification.QuestionSpecs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import lombok.extern.log4j.Log4j2;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Log4j2
public class QuestionRepoTest {

	@Autowired
	QuestionRepo questionRepo;

	@Test
	void injectedComponentsAreNotNull() {
		assertNotNull(questionRepo);
	}

	@Test
	void testFindQuestionNotInCatalog() {
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		String keyword = "";
		Long catalogId = 1L;
		Specification<Question> specification = QuestionSpecs.notInCatalog(keyword, catalogId);
		Page<Question> questions = questionRepo.findAll(specification, pageable);
		log.debug(questions.getContent());
		int expected = 2;
		assertEquals(expected, questions.getContent().size());
	}

	@Test
	void testFindQuestionNotInCatalog2() {
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		String keyword = "";
		Long catalogId = 2L;
		Specification<Question> specification = QuestionSpecs.notInCatalog(keyword, catalogId);
		Page<Question> questions = questionRepo.findAll(specification, pageable);
		log.debug(questions.getContent());
		int expected = 4;
		assertEquals(expected, questions.getContent().size());
	}

	@Test
	void testFindAllByExam() {
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		String keyword = "";
		Long examId = 1L;
		Specification<Question> specification = QuestionSpecs.inExamAndContentLike(keyword, examId);
		Page<Question> questions = questionRepo.findAll(specification, pageable);
		int expected = 10;
		int actual = questions.getContent().size();
		assertEquals(expected, actual);
	}

	@Test
	void testFindByIdInPagable() {
		List<Long> ids = new ArrayList<>();
		ids.add(1L);
		ids.add(2L);
		Specification<Question> specification = QuestionSpecs.hasIdIn(ids);
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		Page<Question> qPage = questionRepo.findAll(specification, pageable);
		int expected = 2;
		int actual = qPage.getContent().size();
		assertEquals(expected, actual);
		log.debug(qPage.getContent());
	}

	@Test
	void testFindByIdInList() {
		List<Long> ids = new ArrayList<>();
		ids.add(1L);
		ids.add(2L);
		Specification<Question> specification = QuestionSpecs.hasIdIn(ids);
		List<Question> questions = questionRepo.findAll(specification);
		int expected = 2;
		int actual = questions.size();
		assertEquals(expected, actual);
		log.debug(questions);
	}

	@Test
	void testFindAllByCatalogId() {
		Long catalogId = 1L;
		Specification<Question> specification = QuestionSpecs.inCatalog(catalogId);
		List<Question> questions = questionRepo.findAll(specification);
		int expected = 10;
		int actual = questions.size();
		assertEquals(expected, actual);
	}

	@Test
	void testFindAllByCatalogIdWithKeyword() {
		String keyword = "Hello World";
		Long catalogId = 1L;
		Specification<Question> specification = QuestionSpecs.inCatalogAndContentLike(keyword, catalogId);
		List<Question> questions = questionRepo.findAll(specification);
		int expected = 1;
		int actual = questions.size();
		assertEquals(expected, actual);
		log.debug(questions);
	}

}
