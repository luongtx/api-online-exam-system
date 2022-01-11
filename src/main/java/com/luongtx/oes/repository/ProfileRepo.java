package com.luongtx.oes.repository;

import java.util.Optional;

import com.luongtx.oes.entity.Profile;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepo extends JpaRepository<Profile, Long> {

    Optional<Profile> findOne(Specification<Profile> specification);
    
}
