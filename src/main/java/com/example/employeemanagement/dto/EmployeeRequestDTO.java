package com.example.employeemanagement.dto;

import lombok.Data;

@Data
public class EmployeeRequestDTO {           // **Request DTO** → Controls what data the **client can send IN**

    private String name;
    private String email;
    private String department;
    private double salary;
}
