package com.luongtx.oes.repository;

import java.util.List;

import com.luongtx.oes.entity.UserExam;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExamRepo extends JpaRepository<UserExam, Long> {

    List<UserExam> findAll(Specification<UserExam> specification, Pageable pageable);

}
