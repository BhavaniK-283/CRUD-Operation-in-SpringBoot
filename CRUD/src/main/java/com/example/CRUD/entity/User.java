package com.example.CRUD.entity;



import com.example.CRUD.enumurators.UserStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;

        @Column(nullable = false)
        private String name;

        @Column(name = "phone_number")
        private String phoneNumber;

        @Column(name = "unique_code", nullable = false, unique = true)
        private String uniqueCode;

        @Column(name = "email_id", nullable = false, unique = true)
        private String emailId;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private UserStatus status;

        @Column(name = "created_by")
        private Integer createdBy;

        @Column(name = "updated_by")
        private Integer updatedBy;

        @CreationTimestamp
        @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;
}