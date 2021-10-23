package com.luongtx.oes.repository;

import com.luongtx.oes.entity.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExamRepo extends JpaRepository<Exam, Long> {

    @Query("select e from exam e where e.examCode like %?1% or e.title like %?1% or e.description like %?1%")
    Page<Exam> findAll(Pageable pageable, String keyword);
}
