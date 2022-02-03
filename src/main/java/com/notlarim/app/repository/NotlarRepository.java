package com.notlarim.app.repository;

import com.notlarim.app.domain.Notlar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Notlar entity.
 */
@Repository
public interface NotlarRepository extends JpaRepository<Notlar, Long> {}
