package com.luongtx.oes.repository;

import com.luongtx.oes.entity.UserExam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserExamRepo extends JpaRepository<UserExam, Long> {
    List<UserExam> getAllByUserIdOrderByFinishedDateDesc(Long userId);
}
