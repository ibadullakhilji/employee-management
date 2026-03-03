package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Spring pls create the implementation for me

    // Spring Data JPA
            // automatically generates implementation at runtime
            // you only define method signatures
            // spring creates the actual class behind the scenes


    // Way 1 — Method Name Query (Simplest)
    // Spring reads the method name and builds the SQL automatically
    List<Employee> findByDepartment(String department);       // Custom query method to find employees by department

    List<Employee> findByNameContainingIgnoreCase(String keyword);     // Custom query method to search employees by name (case-insensitive)

    List<Employee>  findBySalaryGreaterThan(double salary);       // Custom query method to find employees with salary greater than a specified amount

    List<Employee>  findBySalaryBetween(double minSalary, double maxSalary);       // Custom query method to find employees with salary between a specified range


    // Way 2 — @Query with JPQL
    @Query("SELECT e FROM Employee e WHERE e.department = :dept AND e.salary >= :minSalary")       // Custom JPQL query to find employees by department and minimum salary
    List<Employee> findByDepartmentAndMinSalary( @Param("dept") String department, @Param("minSalary") double minSalary);       // Custom query method to find employees by department and minimum salary

    // @Query lets you write your own query, :dept and :minSalary are named parameters ,@Param("dept") binds the method parameter to :dept in the query


    // Way 3 — Native SQL Query
    @Query(value = "SELECT * FROM employees WHERE department = :dept", nativeQuery = true)
    List<Employee> findByDepartmentNative(@Param("dept") String department);
    // nativeQuery = true tells Spring this is real SQL not JPQL

}