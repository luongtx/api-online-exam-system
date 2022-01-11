package com.luongtx.oes.repository.specification;

import static com.luongtx.oes.constants.SpecConstants.*;
import com.luongtx.oes.entity.Exam;
import com.luongtx.oes.utils.StringUtils;

import org.springframework.data.jpa.domain.Specification;

public class ExamSpecs {

	public static Specification<Exam> hasFieldValueLike(String keyword) {
		return (root, query, cb) -> {
			String searchString = StringUtils.toSearchString(keyword);
			return cb.or(cb.like(root.get(TITLE), searchString),
					cb.like(root.get(DESCRIPTION), searchString),
					cb.like(root.get(EXAM_CODE), searchString));
		};
	}
}
