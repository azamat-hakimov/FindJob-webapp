package com.example.findjob.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.findjob.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long>{
    Employee findByOwnerId(long ownerId);
    List<Employee> findByOccupation(String occupation);
    boolean existsByOwnerId(long id);
}
