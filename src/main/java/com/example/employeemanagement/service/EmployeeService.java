package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service    // marks class a service/business logic layer, manages it as a bean
public class EmployeeService {
    private final Map<Long, Employee> employeeMap= new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public Employee createEmployee(Employee employee){
        Long id = idCounter.getAndIncrement();
        employee.setId(id);
        employeeMap.put(id,employee);
        return employee;
    }

    public List<Employee> getAllEmployees(){
        return new ArrayList<>(employeeMap.values());
    }

    public Employee getEmployeeById(Long id){
        return employeeMap.get(id);
    }

    public Employee updateEmployee(Long id,Employee updatedEmploye){
        Employee existing = employeeMap.get(id);
        if(existing == null){
            return null;
        }
        existing.setName(updatedEmploye.getName());
        existing.setEmail(updatedEmploye.getEmail());
        existing.setDepartment(updatedEmploye.getDepartment());
        existing.setSalary(updatedEmploye.getSalary());
        return existing;
    }

    public boolean deleteEmployee(Long id){
        return employeeMap.remove(id) != null;
    }
}
