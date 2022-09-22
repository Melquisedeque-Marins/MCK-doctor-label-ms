package com.melck.doctor.ms.resources;

import com.melck.doctor.ms.entities.Doctor;
import com.melck.doctor.ms.entities.Doctor;
import com.melck.doctor.ms.services.DoctorService;
import com.melck.doctor.ms.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorResource {

    @Autowired
    private DoctorService service;

    @PostMapping
    public ResponseEntity<Doctor> insert(@RequestBody Doctor doctor){
        Doctor newDoctor = service.insert(doctor);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDoctor.getDoctorId()).toUri();
        return ResponseEntity.created(uri).body(newDoctor);
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<Doctor> findById(@PathVariable Long doctorId){
        Doctor doctor = service.findById(doctorId);
        return ResponseEntity.ok().body(doctor);
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> findAll(){
        List<Doctor> doctors = service.findAll();
        return ResponseEntity.ok().body(doctors);
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Doctor> delete (@PathVariable Long doctorId){
        service.delete(doctorId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<Doctor> update(@PathVariable Long doctorId, @Valid @RequestBody Doctor doctor){
        Doctor updatedDoctor = service.update(doctorId, doctor);
        return ResponseEntity.ok().body(updatedDoctor);
    }




}
