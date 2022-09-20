package com.melck.doctor.ms.repositories;

import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
