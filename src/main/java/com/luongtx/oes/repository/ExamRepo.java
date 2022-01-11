package com.luongtx.oes.repository;

import com.luongtx.oes.entity.Exam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepo extends JpaRepository<Exam, Long> {

    Page<Exam> findAll(Specification<Exam> specification, Pageable pageable);

}
