package com.suti.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Employee addEmployee(@RequestBody Employee employee) {
        Employee emp = employeeService.add(employee);
        return emp;
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Employee getEmployee(@PathVariable("id") String id) {
        Employee emp = employeeService.get(id);
        log.debug(emp.toString());
        return emp;
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Employee> employees() {
        List<Employee> employees = employeeService.getAll();
        return employees;
    }
}
