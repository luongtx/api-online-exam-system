package com.luongtx.oes.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.luongtx.oes.entity.Exam;
import com.luongtx.oes.repository.ExamRepo;
import com.luongtx.oes.repository.specification.ExamSpecs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExamRepoTest {

	@Autowired
	ExamRepo examRepo;

	@Test
	void testCountByExam() {
		Long id = 1L;
		Exam exam = examRepo.findById(id).orElse(null);
		assertNotNull(exam);
		int expected = 10;
		int actual = exam.getQuestions().size();
		assertEquals(expected, actual);
	}

	@Test
	void testFindByValueLike() {
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		String searchKey = "";
		Specification<Exam> specification = ExamSpecs.hasFieldValueLike(searchKey);
		Page<Exam> pageExams = examRepo.findAll(specification, pageable);
		int expected = 3;
		int actual = pageExams.getContent().size();
		assertEquals(expected, actual);
	}

	@Test
	void testFindByValueLike2() {
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		String searchKey = "oracle";
		Specification<Exam> specification = ExamSpecs.hasFieldValueLike(searchKey);
		Page<Exam> pageExams = examRepo.findAll(specification, pageable);
		int expected = 1;
		int actual = pageExams.getContent().size();
		assertEquals(expected, actual);
	}

	@Test
	void testFindByValueLike3() {
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		String searchKey = "808";
		Specification<Exam> specification = ExamSpecs.hasFieldValueLike(searchKey);
		Page<Exam> pageExams = examRepo.findAll(specification, pageable);
		int expected = 1;
		int actual = pageExams.getContent().size();
		assertEquals(expected, actual);
	}
}
