package com.luongtx.oes.repository;

import com.luongtx.oes.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepo extends JpaRepository<Exam, Long> {

//    @Query("update user_exam ue set ue.score=?3, ue.status=?4 where ue.userId=?1 and ue.examId=?2")
//    void updateExamResultForUser(Long userId, Long examId, Integer score, Boolean status);
}
