package com.notlarim.app.repository;

import com.notlarim.app.domain.PersonelTanim;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PersonelTanim entity.
 */
@Repository
public interface PersonelTanimRepository extends JpaRepository<PersonelTanim, Long> {}
