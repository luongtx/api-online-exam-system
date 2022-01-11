package com.luongtx.oes.repository.specification;

import static com.luongtx.oes.constants.SpecConstants.*;
import com.luongtx.oes.entity.Catalog;
import com.luongtx.oes.utils.StringUtils;

import org.springframework.data.jpa.domain.Specification;

public class CatalogSpecs {

	public static Specification<Catalog> hasNameLike(String name) {
		return (root, query, cb) -> {
			return cb.like(root.get(NAME), StringUtils.toSearchString(name));
		};
	}
}
