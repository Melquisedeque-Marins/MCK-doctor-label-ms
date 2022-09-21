    package com.melck.doctor.ms.entities;

    import lombok.*;

    import javax.persistence.*;
    import java.time.Instant;
    import java.util.ArrayList;
    import java.util.List;


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

        @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
        private Instant createdAt;

        @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
        private Instant updatedAt;

        @OneToOne
        @JoinColumn(name = "case_id")
        private Case aCase;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "doctor_id")
        private Doctor doctor;


        @OneToOne
        @JoinColumn(name = "label_id")
        private Label label;

        @PrePersist
        public void preCreated(){
            createdAt = Instant.now();
        }
        @PreUpdate
        public void preUpdate(){
            updatedAt = Instant.now();
        }

    }
