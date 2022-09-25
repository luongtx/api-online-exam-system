package com.luongtx.oes.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.luongtx.oes.entity.UserExam;
import com.luongtx.oes.repository.UserExamRepo;
import com.luongtx.oes.repository.specification.UserExamSpecs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import lombok.extern.log4j.Log4j2;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Log4j2
public class UserExamRepoTest {

	@Autowired
	UserExamRepo userExamRepo;

	@Test
	void testGetMostRecentExamByUser() {
		Long userId = 2L;
		Specification<UserExam> specification = UserExamSpecs.mostRecentExamByUser(userId);
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		List<UserExam> userExams = userExamRepo.findAll(specification, pageable);
		int expected = 2;
		int actual = userExams.size();
		assertEquals(expected, actual);
		expected = 2;
		actual = userExams.get(0).getId().intValue();
		assertEquals(expected, actual);
	}
}
