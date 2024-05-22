package com.example.findjob.service;

import java.util.List;

import com.example.findjob.entity.Employee;

public interface EmployeeService {
    void setEmployee(Employee employee);
    Employee displayEmployee(long ownerId);
    List<Employee> getEmployeeByOccupations(String occupation);
    boolean checkUserSubmittedBefore(long ownerId);
}
