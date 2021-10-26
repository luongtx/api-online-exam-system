package com.luongtx.oes.repository;

import com.luongtx.oes.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    @Query("select c from category c where c.name like %:key%")
    Page<Category> findAll(Pageable pageable, @Param("key") String searchKey);
}
