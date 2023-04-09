package com.uiowa.fse_project.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.uiowa.fse_project.model.Employee;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    Employee getEmployeeById(long id);
    void deleteEmployeeById(long id);
    Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}