package com.example.employeemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data           // lombok , auto-gen getter,setter,toString,equals
@NoArgsConstructor  // generates no-args contructor
@AllArgsConstructor     // generates a constructor with all fields as constructor
public class Employee {         // This class Represents business data

    private Long id;
    private String name;
    private String email;
    private String department;
    private double salary;

}
