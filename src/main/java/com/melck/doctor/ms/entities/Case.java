package com.melck.doctor.ms.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_case")
public class Case {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long caseId;

    @Column(columnDefinition = "TEXT")
    private String caseDescription;

    @OneToOne
    @JoinColumn(name = "label_id")
    private Label label;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    @PrePersist
    public void preCreated(){
        createdAt = Instant.now();
    }
    @PreUpdate
    public void preUpdate(){
        updatedAt = Instant.now();
    }
}

