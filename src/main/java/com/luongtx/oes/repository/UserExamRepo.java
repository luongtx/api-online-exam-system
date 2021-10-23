package com.luongtx.oes.repository;

import com.luongtx.oes.entity.UserExam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserExamRepo extends JpaRepository<UserExam, Long> {
    @Query("select ue from user_exam ue where ue.userId=?1 order by ue.finishedDate desc ")
    List<UserExam> getMostRecentByUserId(Long userId, Pageable pageable);
}
