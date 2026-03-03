package com.example.employeemanagement.dto;

import lombok.Data;

@Data
public class EmployeeResponseDTO {      // **Response DTO** → Controls what data the **client gets OUT**
    private Long id;
    private String name;
    private String email;
    private String department;
    private double salary;
}