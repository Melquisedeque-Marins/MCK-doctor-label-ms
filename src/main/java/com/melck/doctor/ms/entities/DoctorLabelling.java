    package com.melck.doctor.ms.entities;

    import lombok.*;

    import javax.persistence.*;
    import java.time.Instant;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @Entity
    @Table(name = "tb_doctor_labelling")
    public class DoctorLabelling {

        @EqualsAndHashCode.Include
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Instant createdAt;

        @OneToOne
        @JoinColumn(name = "case_id")
        private Case cases;

        @ManyToOne
        @JoinColumn(name = "doctor_id")
        private Doctor doctor;

    }
