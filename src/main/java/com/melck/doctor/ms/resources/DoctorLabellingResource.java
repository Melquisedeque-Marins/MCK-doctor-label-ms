package com.melck.doctor.ms.resources;

import com.melck.doctor.ms.dtos.CaseDTO;
import com.melck.doctor.ms.dtos.DoctorLabellingDTO;
import com.melck.doctor.ms.dtos.ResponseCaseDTO;
import com.melck.doctor.ms.dtos.ResponseDoctorLabellingDTO;
import com.melck.doctor.ms.entities.Case;
import com.melck.doctor.ms.entities.DoctorLabelling;
import com.melck.doctor.ms.entities.Label;
import com.melck.doctor.ms.services.CaseService;
import com.melck.doctor.ms.services.DoctorLabellingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/doctor-labellings")
public class DoctorLabellingResource {

    private final DoctorLabellingService service;
    private final CaseService caseService;

    @PostMapping
    public ResponseEntity<ResponseDoctorLabellingDTO> insert(@Valid @RequestBody DoctorLabellingDTO labellingDTO){
       ResponseDoctorLabellingDTO doctorLabelling = service.insert(labellingDTO);
       URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(doctorLabelling.getId()).toUri();
       return ResponseEntity.created(uri).body(doctorLabelling);
    }

    @PostMapping("/cases")
    @CacheEvict(value = "case", allEntries = true)
    public ResponseEntity<ResponseCaseDTO> insertCase(@Valid @RequestBody CaseDTO caseDTO){
        ResponseCaseDTO newCase = caseService.insert(caseDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCase.getCaseId()).toUri();
        return ResponseEntity.created(uri).body(newCase);
    }

    @GetMapping("/cases/{caseId}")
    public ResponseEntity<ResponseCaseDTO> findCaseById(@PathVariable Long caseId){
        Case c = caseService.findById(caseId);
        ResponseCaseDTO dto = new ResponseCaseDTO(c);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/cases")
    public ResponseEntity<Page<ResponseCaseDTO>> findAllCases(Pageable pageable) {
        Page<ResponseCaseDTO> cases = caseService.findAllCases(pageable);
        return ResponseEntity.ok().body(cases);
    }


    @GetMapping("/cases/label")
    @Cacheable(value = "case")
    public ResponseEntity<Page<ResponseCaseDTO>> findCasesByLabel(
            @RequestParam(value = "code", defaultValue = "") String code,
            Pageable pageable) {
        Page<ResponseCaseDTO> cases = caseService.findCasesByLabel(code.trim(), pageable);
        return ResponseEntity.ok().body(cases);
    }

    @PutMapping("/cases/{caseId}")
    @CachePut(value = "case")
    public ResponseEntity<Case> updateCase(@PathVariable Long caseId, @Valid @RequestBody Case aCase){
        Case updatedCase = caseService.update(caseId, aCase);
        return ResponseEntity.ok().body(updatedCase);
    }

    @PatchMapping("/cases/{caseId}")
    public ResponseEntity<Case> deleteLabelInACase(@PathVariable Long caseId){
        Case updatedCase = caseService.deleteLabel(caseId);
        return ResponseEntity.ok().body(updatedCase);
    }
//    @PatchMapping("cases/{caseId}/updateLabel")
//    public ResponseEntity<Case> updateLabel(@PathVariable Long caseId, @Valid @RequestBody Label label){
//        Case updatedCase = service.updateLabel(caseId, label);
//        return ResponseEntity.ok().body(updatedCase);
//    }




}
