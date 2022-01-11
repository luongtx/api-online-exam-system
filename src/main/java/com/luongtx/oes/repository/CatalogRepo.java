package com.luongtx.oes.repository;

import com.luongtx.oes.entity.Catalog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepo extends JpaRepository<Catalog, Long> {

	Page<Catalog> findAll(Specification<Catalog> specification, Pageable pageable);

	@Procedure
	void deleteCategory(Long id, Boolean cascade);
}
