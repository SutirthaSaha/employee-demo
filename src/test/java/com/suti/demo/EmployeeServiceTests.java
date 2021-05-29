package com.suti.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeServiceTests {
    @Autowired
    private EmployeeService service;

    @MockBean
    private EmployeeRepository repository;

    @Test
    public void getAll() {
        when(repository.findAll()).thenReturn(Stream
                .of(new Employee("123","Suti","SCM"), new Employee("123","Saivi","SRM")).collect(Collectors.toList()));
        assertEquals(2, service.getAll().size());
    }

    @Test
    public void get() {
        when(repository.findById("123")).thenReturn(Optional.of(new Employee("123", "Suti", "SCM")));
        assertNotNull(service.get("123"));
    }

    @Test
    public void saveUserTest() {
        Employee employee = new Employee("123", "Suti", "SRM");
        when(repository.save(employee)).thenReturn(employee);
        assertEquals(employee, service.add(employee));
    }
}