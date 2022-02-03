package com.notlarim.app.repository;

import com.notlarim.app.domain.Rehber;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Rehber entity.
 */
@Repository
public interface RehberRepository extends JpaRepository<Rehber, Long> {}
