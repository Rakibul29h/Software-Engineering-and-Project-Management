package com.mediflow.Backend.entity;

import com.mediflow.Backend.enums.AuthProvider;
import com.mediflow.Backend.enums.Role;
import com.mediflow.Backend.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String googleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider = AuthProvider.CREDENTIAL;

    @Column(nullable = false)
    private boolean emailVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.PATIENT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    @Column(nullable = false)
    private boolean needPasswordChange = false;

    private String imageURL = "";
    private String imagePubolicId = "";

    @Column(nullable = false)
    private boolean isDeleted = false;

    private java.time.LocalDateTime deletedAt;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Patient patient;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Doctor doctor;
}
