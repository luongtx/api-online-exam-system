package com.luongtx.oes.repository;

import com.luongtx.oes.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepo extends JpaRepository<Question, Long> {
    @Query("select count(q.id) from question q where q.exam.id = ?1")
    int countQuestionsByExamId(Long examId);
}
