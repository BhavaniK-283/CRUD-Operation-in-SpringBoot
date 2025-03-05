package com.example.CRUD.repository;

import com.example.CRUD.entity.User;
import com.example.CRUD.enumurators.EnumUserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailId(String emailId);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);

    boolean existsByEmailIdAndIdNot(String emailId, Integer id);

    Page<User> findByStatus(EnumUserStatus status, Pageable pageable);

    List<User> findByNameContainingIgnoreCase(String name);

}
