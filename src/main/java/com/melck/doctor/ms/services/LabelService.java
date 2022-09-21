package com.melck.doctor.ms.services;

import com.melck.doctor.ms.entities.Label;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "label", url = "${url.label-service}/labels")
public interface LabelService {

    @GetMapping("/{id}")
    Label findLabel(@PathVariable Long id);

    @PostMapping
    public Label insert(@RequestBody Label label);

}
