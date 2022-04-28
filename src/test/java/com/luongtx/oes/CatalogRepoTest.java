
package com.luongtx.oes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.luongtx.oes.entity.Catalog;
import com.luongtx.oes.repository.CatalogRepo;
import com.luongtx.oes.repository.specification.CatalogSpecs;

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
public class CatalogRepoTest {

	@Autowired
	CatalogRepo catalogRepo;

	@Test
	void testCountQuestionById() {
		long id = 1L;
		Catalog catalog = catalogRepo.getById(id);
		int expected = 2;
		int actual = catalog.getQuestions().size();
		assertEquals(expected, actual);
	}

	@Test
	void testFindByNameLike() {
		String keyword = "";
		Specification<Catalog> specification = CatalogSpecs.hasNameLike(keyword);
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		Page<Catalog> categoryPage = catalogRepo.findAll(specification, pageable);
		int expected = 4;
		int actual = categoryPage.getContent().size();
		assertEquals(expected, actual);
	}

	@Test
	void testFindByNameLike2() {
		String keyword = "java";
		Specification<Catalog> specification = CatalogSpecs.hasNameLike(keyword);
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		Page<Catalog> categoryPage = catalogRepo.findAll(specification, pageable);
		int expected = 1;
		int actual = categoryPage.getContent().size();
		assertEquals(expected, actual);
	}
}