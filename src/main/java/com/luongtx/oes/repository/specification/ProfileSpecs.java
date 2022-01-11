package com.luongtx.oes.repository.specification;

import com.luongtx.oes.entity.Profile;

import org.springframework.data.jpa.domain.Specification;

import static com.luongtx.oes.constants.SpecConstants.*;

public class ProfileSpecs {

	public static Specification<Profile> hasUsername(String username) {
		return (root, query, cb) -> {
			return cb.equal(root.get(USER).get(USERNAME), username);
		};
	}

	public static Specification<Profile> hasUserId(Long userId) {
		return (root, query, cb) -> {
			return cb.equal(root.get(USER).get(ID), userId);
		};
	}
}
