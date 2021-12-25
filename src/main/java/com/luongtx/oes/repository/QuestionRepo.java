package com.luongtx.oes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.luongtx.oes.entity.Question;

public interface QuestionRepo extends JpaRepository<Question, Long> {
	@Query("select count(q.id) from question q where q.exam.id = ?1")
	int countQuestionsByExamId(Long examId);

	@Query("select count(q) from question q join category c on q.category.id=c.id "
			+ "where q.category.id = ?1 or c.categoryParent.id = ?1")
	long countQuestionsByCategoryId(Long categoryId);

	@Query("select q from question q where q.exam.id = ?1")
	Page<Question> findAllByExamId(Long examId, Pageable pageable);

	@Query("select q from question q join category c on q.category.id=c.id "
			+ "where q.category.id = ?1 or c.categoryParent.id = ?1 and q.content like %?2%")
	Page<Question> findAllByCategory(Long categoryId, Pageable pageable, String searchKey);

	@Query("select q from question q join category c on q.category.id=c.id "
			+ "where q.category.id = ?1 or c.categoryParent.id = ?1")
	List<Question> findAllByCategory(Long categoryId);

	@Query("select q from question q where q.content like  %?1%")
	Page<Question> findAll(Pageable pageable, String searchKey);

	@Query("select q from question q where q.content like %?1% and q.category.id is null or q.category.id <> ?2")
	Page<Question> findAllExceptCatalog(Pageable pageable, String searchKey, Long catalogId);
}
