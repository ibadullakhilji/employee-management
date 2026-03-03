package com.example.employeemanagement.exception;

public class EmployeeNotFoundException  extends RuntimeException{       // We extend RuntimeException — so Spring can catch it automatically

    public EmployeeNotFoundException(Long id){
        super("Employee with ID " + id + " not found.");        // This gets thrown whenever an employee is not found
    }
}
