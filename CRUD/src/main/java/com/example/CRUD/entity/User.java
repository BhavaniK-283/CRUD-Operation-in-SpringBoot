package com.example.CRUD.entity;
import com.example.CRUD.enumurators.EnumUserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor

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
        private EnumUserStatus status;

        @Column(name = "created_by")
        private String createdBy;

        @Column(name = "updated_by")
        private String updatedBy;

        @CreationTimestamp
        @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;


        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        public String getUniqueCode() {
                return uniqueCode;
        }

        public void setUniqueCode(String uniqueCode) {
                this.uniqueCode = uniqueCode;
        }

        public String getEmailId() {
                return emailId;
        }

        public void setEmailId(String emailId) {
                this.emailId = emailId;
        }

        public EnumUserStatus getStatus() {
                return status;
        }

        public void setStatus(EnumUserStatus status) {
                this.status = status;
        }

        public String getCreatedBy() {
                return createdBy;
        }

        public void setCreatedBy(String createdBy) {
                this.createdBy = createdBy;
        }

        public String getUpdatedBy() {
                return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
                this.updatedBy = updatedBy;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
                return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
        }

}