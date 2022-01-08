package com.luongtx.oes.repository;

import java.util.List;

import com.luongtx.oes.entity.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepo extends JpaRepository<Question, Long> {
	@Query("select count(q.id) from question q where q.exam.id = ?1")
	int countQuestionsByExamId(Long examId);

	@Query("select count(q) from question q join catalog c on q.catalog.id=c.id "
			+ "where q.catalog.id = ?1 or c.catalogParent.id = ?1")
	long countQuestionsByCatalogId(Long catalogId);

	@Query("select q from question q where q.exam.id = ?1 and q.content like %?2%")
	Page<Question> findAllByExam(Long examId, Pageable pageable, String searchKey);

	@Query("select q from question q join catalog c on q.catalog.id=c.id "
			+ "where q.catalog.id = ?1 or c.catalogParent.id = ?1 and q.content like %?2%")
	Page<Question> findAllByCatalog(Long catalogId, Pageable pageable, String searchKey);

	@Query("select q from question q where q.content like  %?1%")
	Page<Question> findAll(Pageable pageable, String searchKey);

	@Query("select q from question q left join catalog c on q.catalog.id=c.id "
			+ "where q.content like %?1% "
			+ "and (q.catalog.id is null or q.catalog.id <> ?2) "
			+ "and (c.catalogParent.id is null or c.catalogParent.id <> ?2) "
			+ "order by q.id")
	Page<Question> findAllExceptCatalog(Pageable pageable, String searchKey, Long catalogId);

	List<Question> findAllByIdIn(List<Long> ids);

	@Query("select q from question q "
			+ "where q.content like %?1% "
			+ "and (q.exam.id is null or q.exam.id <> ?2) "
			+ "order by q.id")
	Page<Question> findAllExceptExam(Pageable pageable, String searchKey, Long examId);
}
