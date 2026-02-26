package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service    // marks class a service/business logic layer, manages it as a bean
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id){
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee updateEmployee(Long id,Employee updatedEmploye){
        Employee existing = employeeRepository.findById(id).orElse(null);
        if(existing == null){
            return null;
        }
        existing.setName(updatedEmploye.getName());
        existing.setEmail(updatedEmploye.getEmail());
        existing.setDepartment(updatedEmploye.getDepartment());
        existing.setSalary(updatedEmploye.getSalary());
        return employeeRepository.save(existing);
    }

    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }
}
