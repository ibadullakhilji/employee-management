package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeRequestDTO;
import com.example.employeemanagement.dto.EmployeeResponseDTO;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service    // marks class a service/business logic layer, manages it as a bean
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto){
        Employee employee = toEntity(dto);      // Convert RequestDTO to Entity
        Employee savedEmployee = employeeRepository.save(employee);     // Save to DB
        return  toResponseDTO(savedEmployee);
    }

    // Create Employees by bulk
    public List<Employee> createEmployeesByBulk(List<Employee> employees){
        return employeeRepository.saveAll(employees);
    }

    public List<EmployeeResponseDTO> getAllEmployees(int page, int size , String sortBy){
        Pageable pageable=PageRequest.of(page, size , Sort.by(sortBy));                 // Pageable is an interface that holds pagination info
        return employeeRepository.findAll(pageable)                        // PageRequest.of(page, size, Sort.by(sortBy)) creates a pageable object
                .stream()                                                                // — "give me page 0, with 10 items, sorted by id"
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EmployeeResponseDTO getEmployeeById(Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(            // orElseThrow() — if employee not found, throw our custom exception
                () -> new EmployeeNotFoundException(id)
                );
        return toResponseDTO(employee);
    }

    public EmployeeResponseDTO updateEmployee(Long id,EmployeeRequestDTO dto){
        Employee existing = employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException(id)
        );

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setDepartment(dto.getDepartment());
        existing.setSalary(dto.getSalary());
        return toResponseDTO(employeeRepository.save(existing));
    }

    public void deleteEmployee(Long id){
        employeeRepository.findById(id)
                        .orElseThrow( () -> new EmployeeNotFoundException(id));
        employeeRepository.deleteById(id);
    }

    public List<EmployeeResponseDTO> getEmployeesByDepEmployees(String department){
        return employeeRepository.findByDepartment(department)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeResponseDTO> searchEmployeesByName(String keyword){
        return employeeRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeResponseDTO> getEmployeesAboveSalary(double salary){
        return employeeRepository.findBySalaryGreaterThan(salary)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeResponseDTO> getEmployeesBySalaryRange(double minSalary, double maxSalary){
        return employeeRepository.findBySalaryBetween(minSalary, maxSalary)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeResponseDTO> getByDepartmentAndMinSalary(String department, double minSalary){
        return employeeRepository.findByDepartmentAndMinSalary(department, minSalary)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    // DTO

    // Convert RequestDTO → Entity (when saving to DB)
    private Employee toEntity(EmployeeRequestDTO dto){      // converts incoming RequestDTO to Employee entity before saving to DB
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setSalary(dto.getSalary());
        return employee;
    }

    // Convert Entity → ResponseDTO (when sending data to client)
    private EmployeeResponseDTO toResponseDTO(Employee employee){       // converts Employee entity to ResponseDTO before sending to client
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setDepartment(employee.getDepartment());
        dto.setSalary(employee.getSalary());
        return dto;

    }
}
