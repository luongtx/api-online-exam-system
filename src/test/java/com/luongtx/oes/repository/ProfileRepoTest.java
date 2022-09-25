package com.luongtx.oes.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.luongtx.oes.entity.Profile;
import com.luongtx.oes.repository.ProfileRepo;
import com.luongtx.oes.repository.specification.ProfileSpecs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import lombok.extern.log4j.Log4j2;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Log4j2
public class ProfileRepoTest {
	@Autowired
	ProfileRepo profileRepo;

	@Test
	void testFindByUserName() {
		String username = "admin";
		Specification<Profile> specification = ProfileSpecs.hasUsername(username);
		Optional<Profile> optional = profileRepo.findOne(specification);
		assertTrue(optional.isPresent());
		int expected = 1;
		int actual = optional.get().getId().intValue();
		assertEquals(expected, actual);
	}

	@Test
	void testFindByUserName2() {
		String username = "luong";
		Specification<Profile> specification = ProfileSpecs.hasUsername(username);
		Optional<Profile> optional = profileRepo.findOne(specification);
		assertTrue(optional.isPresent());
		int expected = 2;
		int actual = optional.get().getId().intValue();
		assertEquals(expected, actual);
	}

	@Test
	void testFindByUserId() {
		Long userId = 1L;
		Specification<Profile> specification = ProfileSpecs.hasUserId(userId);
		Optional<Profile> optional = profileRepo.findOne(specification);
		assertTrue(optional.isPresent());
		int expected = 1;
		int actual = optional.get().getId().intValue();
		assertEquals(expected, actual);
	}
}
