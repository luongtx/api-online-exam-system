package com.luongtx.oes.repository.specification;

import static com.luongtx.oes.constants.SpecConstants.*;
import com.luongtx.oes.entity.UserExam;

import org.springframework.data.jpa.domain.Specification;

public class UserExamSpecs {
	
	public static Specification<UserExam> mostRecentExamByUser(Long userId) {
		return (root, query, cb) -> {
			query.orderBy(cb.desc(root.get(FININISHED_DATE)));
			return cb.equal(root.get(USERID), userId);
		};
	}
}
