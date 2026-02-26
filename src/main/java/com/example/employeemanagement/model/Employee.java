package com.example.employeemanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity             // Marks class As DB table
@Table(name = "employees")              // Table name
@Data           // lombok , auto-gen getter,setter,toString,equals
@NoArgsConstructor  // generates no-args contructor
@AllArgsConstructor     // generates a constructor with all fields as constructor
public class Employee {         // This class Represents business data

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // Auto increment ID
    private Long id;

    private String name;
    private String email;
    private String department;
    private double salary;

}
