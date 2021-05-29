package com.suti.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee add(Employee employee) {
        Employee emp = employeeRepository.save(employee);
        return emp;
    }

    public Employee get(String id) {
        Employee emp = employeeRepository.findById(id).orElse(new Employee());
        log.debug(emp.toString());
        return emp;
    }

    public List<Employee> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }
}