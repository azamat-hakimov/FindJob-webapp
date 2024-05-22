package com.example.findjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.findjob.entity.User;

@Repository
public interface LoginRepository extends JpaRepository<User, Long>{
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
