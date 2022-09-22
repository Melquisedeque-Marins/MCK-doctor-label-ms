package com.melck.doctor.ms.services;

import com.melck.doctor.ms.entities.Doctor;
import com.melck.doctor.ms.repositories.DoctorRepository;
import com.melck.doctor.ms.services.exceptions.DataIntegrityViolationException;
import com.melck.doctor.ms.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository repository;

    @Transactional
    public Doctor insert(Doctor d) {
        Doctor newDoctor = repository.save(d);
        return newDoctor;
    }

    @Transactional(readOnly = true)
    public Doctor findById(Long doctorId) {
        return repository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with id: " + doctorId + " must be founded"));
    }
    @Transactional(readOnly = true)
    public List<Doctor> findAll() {
        return repository.findAll();
    }

    public void delete(Long DoctorId) {
        Doctor c = findById(DoctorId);
        try {
            repository.delete(c);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("the entity with id: " + c.getDoctorId() + " cannot be deleted");
        }
    }

    @Transactional
    public Doctor update(Long doctorId, Doctor doctor) {
        Doctor actualDoctor = findById(doctorId);
        actualDoctor.setName(doctor.getName());
        return repository.save(actualDoctor);
    }
}
