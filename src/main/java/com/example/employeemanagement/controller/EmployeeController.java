package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeRequestDTO;
import com.example.employeemanagement.dto.EmployeeResponseDTO;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")       // It tells this method when this URL is called Run this method
@Tag( name = "Employee Management",  description = "API's for managing employees")
public class EmployeeController {

    @Autowired      // Spring pls give me this Object automatically
    private EmployeeService employeeService;

    // CREATE employee
    @Operation( summary = "Create a new employee", description = "Adds a new employee to the database ")
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeRequestDTO dto){
        EmployeeResponseDTO created= employeeService.createEmployee(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Bulk Insert
    @PostMapping("/bulk")
    public ResponseEntity<List<Employee>> createEmployeesbyBulk(@RequestBody List<Employee> employees){
        List<Employee> created = employeeService.createEmployeesByBulk(employees);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET all employee
    @Operation(summary = "Get all employees", description = "Returns paginated and sorted list of employees")
    @GetMapping                         // Page<Employee> in Spring wrapper that returns data + metadata like total pages, total elements
    public List<EmployeeResponseDTO> getAllEmployees(@RequestParam(defaultValue = "0") int page,                   // RequestParam reads query params from url
                                                     @RequestParam(defaultValue = "10") int size,                  // defaultValue means if user doesn't pass them, use these defaults
                                                     @RequestParam(defaultValue = "id") String sortBy ){

        return employeeService.getAllEmployees(page, size, sortBy);
    }

    // GET employee BY id
    @Operation(summary = "Get employee by ID", description = "Returns a single employee by their ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id){
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    // UPDATE employee
    @Operation(summary = "Update employee", description = "Updates an existing employee by their ID")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDTO dto){
        return new ResponseEntity<>(employeeService.updateEmployee(id, dto), HttpStatus.OK);
    }

    // Delete Employee
    @Operation(summary = "Delete employee", description = "Deletes an employee by their ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }

    // Get Employees by Department
    @Operation(summary = "Get employees by department")
    @GetMapping("/department/{dept}")
    public List<EmployeeResponseDTO> getByDepartment(@PathVariable String dept){
        return employeeService.getEmployeesByDepEmployees(dept);
    }

    // Search Employees by Name
    @Operation(summary = "Search employees by name keyword")
    @GetMapping("/search")
    public List<EmployeeResponseDTO> searchByName(@RequestParam String keyword){
        return employeeService.searchEmployeesByName(keyword);
    }

    // Get Employees above Salary
    @Operation(summary = "Get employees above a salary")
    @GetMapping("/salary/above")
    public List<EmployeeResponseDTO> aboveSalary(@RequestParam double salary){
        return employeeService.getEmployeesAboveSalary(salary);
    }

    // Get Employees by Salary Range
    @Operation(summary = "Get employees within a salary range")
    @GetMapping("/salary/range")
    public List<EmployeeResponseDTO> salaryRange(@RequestParam double minSalary, @RequestParam double maxSalary){
        return employeeService.getEmployeesBySalaryRange(minSalary, maxSalary);
    }

    // Get Employees by Department and Minimum Salary
    @Operation(summary = "Filter employees by department and minimum salary")
    @GetMapping("/filter")
    public List<EmployeeResponseDTO> filterByDeptAndSalary(@RequestParam String department, @RequestParam double minSalary){
        return employeeService.getByDepartmentAndMinSalary(department, minSalary);
    }


}


/*
http://localhost:8080/employees?page=0&size=2&sortBy=name
                                    ↑      ↑          ↑
                                pagenumber page size sort field

*/

// Notice how clean the controller is now!** No null checks, no if statements — just pure logic. All error handling is done by the Global Exception Handler!