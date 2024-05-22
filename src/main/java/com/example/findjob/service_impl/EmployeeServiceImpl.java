package com.example.findjob.service_impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.findjob.entity.Employee;
import com.example.findjob.repository.EmployeeRepo;
import com.example.findjob.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepo employeeRepo;

    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public void setEmployee(Employee employee) {
        employeeRepo.save(employee);
    }

    @Override
    public Employee displayEmployee(long ownerId) {
        return employeeRepo.findByOwnerId(ownerId);
    }

    @Override
    public List<Employee> getEmployeeByOccupations(String occupation) {
        return employeeRepo.findByOccupation(occupation);
    }

    @Override
    public boolean checkUserSubmittedBefore(long ownerId) {
        return employeeRepo.existsByOwnerId(ownerId);
    }
    
}
