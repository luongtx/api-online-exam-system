package com.luongtx.oes.repository;

import com.luongtx.oes.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepo extends JpaRepository<Profile, Long> {

    @Query("select p from profile p where p.user.id=?1")
    Profile findByUserId(Long userId);

    @Query("select p from profile p where p.user.username=?1")
    Profile findByUsername(String username);
}
