package com.melck.doctor.ms.repositories;

import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {
}
