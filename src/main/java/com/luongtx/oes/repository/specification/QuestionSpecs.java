package com.luongtx.oes.repository.specification;

import static com.luongtx.oes.constants.SpecificationConstants.CATALOG;
import static com.luongtx.oes.constants.SpecificationConstants.CATALOG_PARENT;
import static com.luongtx.oes.constants.SpecificationConstants.CONTENT;
import static com.luongtx.oes.constants.SpecificationConstants.EXAM;
import static com.luongtx.oes.constants.SpecificationConstants.ID;

import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import com.luongtx.oes.entity.Catalog;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.utils.StringUtils;

import org.springframework.data.jpa.domain.Specification;

public class QuestionSpecs {

	public static Specification<Question> notInCatalog(String keyword, Long catalogId) {
		return (root, query, cb) -> {
			Join<Question, Catalog> catalog = root.join(CATALOG, JoinType.LEFT);
			Predicate contentLikePred = cb.like(root.get(CONTENT), StringUtils.toSearchString(keyword));

			Path<Long> catalogIdPath = root.get(CATALOG).get(ID);
			Predicate catalogIsNullPred = cb.isNull(catalogIdPath);
			Predicate catalogNotEqualPred = cb.notEqual(catalogIdPath, catalogId);
			Predicate catalogPred = cb.or(catalogIsNullPred, catalogNotEqualPred);

			Path<Long> catalogParentIdPath = catalog.get(CATALOG_PARENT).get(ID);
			Predicate catalogParentIsNullPred = cb.isNull(catalogParentIdPath);
			Predicate catalogParentNotEqualPred = cb.notEqual(catalogParentIdPath, catalogId);
			Predicate catalogParentPred = cb.or(catalogParentIsNullPred, catalogParentNotEqualPred);

			Predicate finalPredicate = cb.and(contentLikePred, catalogPred, catalogParentPred);
			return finalPredicate;
		};
	}

	public static Specification<Question> notInExam(String keyword, Long examId) {
		return (root, query, cb) -> {
			Predicate contentLikePred = cb.like(root.get(CONTENT), StringUtils.toSearchString(keyword));
			Path<Long> examIdPath = root.get(EXAM).get(ID);
			Predicate examIdIsNullPred = cb.isNull(examIdPath);
			Predicate examIdNotEqualPred = cb.notEqual(examIdPath, examId);
			Predicate examPred = cb.or(examIdIsNullPred, examIdNotEqualPred);
			Predicate finalPredicate = cb.and(contentLikePred, examPred);
			return finalPredicate;
		};
	}

	public static Specification<Question> hasContentLike(String keyword) {
		return (root, query, cb) -> {
			return cb.like(root.get(CONTENT), StringUtils.toSearchString(keyword));
		};
	}

	public static Specification<Question> inCatalog(Long catalogId) {
		return (root, query, cb) -> {
			Predicate catalogIdEquals = cb.equal(root.get(CATALOG).get(ID), catalogId);
			Predicate catalogParentIdEquals = cb.equal(root.get(CATALOG).get(CATALOG_PARENT).get(ID), catalogId);
			Predicate finalPredicate = cb.or(catalogIdEquals, catalogParentIdEquals);
			return finalPredicate;
		};
	}

	public static Specification<Question> inCatalogAndContentLike(String keyword, Long catalogId) {
		return hasContentLike(keyword).and(inCatalog(catalogId));
	}

	public static Specification<Question> inExamAndContentLike(String keyword, Long examId) {
		return (root, query, cb) -> {
			Predicate contentLikePred = cb.like(root.get(CONTENT), StringUtils.toSearchString(keyword));
			Predicate examIdEquals = cb.equal(root.get(EXAM).get(ID), examId);
			Predicate finalPredicate = cb.and(contentLikePred, examIdEquals);
			return finalPredicate;
		};
	}

	public static Specification<Question> hasIdIn(List<Long> ids) {
		return (root, query, cb) -> {
			return root.get(ID).in(ids);
		};
	}

}
