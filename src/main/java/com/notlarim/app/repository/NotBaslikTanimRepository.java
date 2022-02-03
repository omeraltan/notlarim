package com.notlarim.app.repository;

import com.notlarim.app.domain.NotBaslikTanim;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NotBaslikTanim entity.
 */
@Repository
public interface NotBaslikTanimRepository extends JpaRepository<NotBaslikTanim, Long> {}
