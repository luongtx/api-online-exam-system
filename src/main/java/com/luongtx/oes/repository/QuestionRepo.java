package com.luongtx.oes.repository;

import java.util.List;

import com.luongtx.oes.entity.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<Question, Long> {

	List<Question> findAll(Specification<Question> specification);

	Page<Question> findAll(Specification<Question> specification, Pageable pageable);

}
