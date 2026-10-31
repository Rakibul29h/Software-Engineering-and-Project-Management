package com.mediflow.Backend.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "patients",
        indexes = {
                @Index(name = "idx_patient_email", columnList = "email"),
                @Index(name = "idx_patient_isDeleted", columnList = "isDeleted")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Patient extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String contactNumber;

    private String address;

    @Column(nullable = false)
    private boolean isDeleted = false;

    private LocalDateTime deletedAt;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "userId",
            nullable = false,
            unique = true
    )
    private User user;
}