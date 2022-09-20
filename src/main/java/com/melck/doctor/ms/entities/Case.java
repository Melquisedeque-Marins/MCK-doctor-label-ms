package com.melck.doctor.ms.entities;

import lombok.*;

import javax.persistence.*;

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
    @JoinColumn(name = "doctor_labelling_id")
    private DoctorLabelling doctorLabelling;
}

