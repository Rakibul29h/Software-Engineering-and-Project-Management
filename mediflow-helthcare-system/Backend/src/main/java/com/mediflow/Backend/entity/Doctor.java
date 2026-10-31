package com.mediflow.Backend.entity;




import com.mediflow.Backend.enums.DoctorVerificationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tools.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "doctors",
        indexes = {
                @Index(name = "idx_doctor_email", columnList = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Doctor extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String address;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private String qualifications;

    @Column(nullable = false)
    private int experienceYears;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(precision = 10, scale = 2)
    private BigDecimal consultationFee;

    private String contactNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DoctorVerificationStatus verificationStatus =
            DoctorVerificationStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String rejectionReason;

    private String reviewedBy;

    private LocalDateTime reviewedAt;

    private String resume;
    private String resumePublicId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private JsonNode additionalFiles;

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