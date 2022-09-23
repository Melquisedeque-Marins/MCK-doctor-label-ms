package com.melck.doctor.ms.repositories;

import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.entities.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {

    @Query("SELECT c FROM Case c WHERE "
            + "( COALESCE(:code) IS NULL OR LOWER(c.label.code) = (LOWER(:code)))" )
    Page<Case> findAllPaged(String code, Pageable pageable);
}
