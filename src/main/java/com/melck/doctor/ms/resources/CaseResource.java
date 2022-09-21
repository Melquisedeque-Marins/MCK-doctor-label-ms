package com.melck.doctor.ms.resources;

import com.melck.doctor.ms.dtos.CaseDTO;
import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.entities.Label;
import com.melck.doctor.ms.services.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cases")
public class CaseResource {

    @Autowired
    private CaseService service;

    @PostMapping
    public ResponseEntity<Case> insert(@RequestBody Case c){
        Case newCase = service.insert(c);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCase.getCaseId()).toUri();
        return ResponseEntity.created(uri).body(newCase);
    }

    @GetMapping("/{caseId}")
    public ResponseEntity<CaseDTO> findById(@PathVariable Long caseId){
        Case c = service.findById(caseId);
        CaseDTO dto = new CaseDTO(c);
        return ResponseEntity.ok().body(dto);
    }


    @GetMapping
    public ResponseEntity<List<Case>> findAll(){
        List<Case> cases = service.findAll();
        return ResponseEntity.ok().body(cases);
    }

    @DeleteMapping("/{caseId}")
    public ResponseEntity<Case> delete (@PathVariable Long caseId){
        service.delete(caseId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{caseId}")
    public ResponseEntity<Case> update(@PathVariable Long caseId, @RequestBody Case aCase){
        Case updatedCase = service.update(caseId, aCase);
        return ResponseEntity.ok().body(updatedCase);
    }
    @PatchMapping("/{caseId}")
    public ResponseEntity<Case> updateLabel(@PathVariable Long caseId, @RequestBody Label label){
        Case updatedCase = service.updateLabel(caseId, label);
        return ResponseEntity.ok().body(updatedCase);
    }




}
