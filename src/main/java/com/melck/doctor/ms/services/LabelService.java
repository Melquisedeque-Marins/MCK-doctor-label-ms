package com.melck.doctor.ms.services;

import com.melck.doctor.ms.entities.Label;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "label", url = "${url.label-service}")
public interface LabelService {


    @PostMapping("/labels")
    public Label insert(@RequestBody Label label);

    @PutMapping("/labels/{labelId}")
    public Label update(@PathVariable("labelId") Long labelId, @RequestBody Label label );

    @DeleteMapping("/labels/{labelId}")
    public Label delete(@PathVariable("labelId") Long labelId);

}
