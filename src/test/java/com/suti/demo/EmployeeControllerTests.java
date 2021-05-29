package com.suti.demo;

import ch.qos.logback.core.net.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class EmployeeControllerTests {
    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @BeforeAll
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .build();
    }

    @Test
    public void getAllTest() throws Exception {
        when(employeeService.getAll()).thenReturn(Stream
                .of(new Employee("123","Suti","SCM"), new Employee("123","Saivi","SRM")).collect(Collectors.toList()));
        mockMvc.perform(get("/getAll"))
                .andExpect(status().isOk());

        verify(employeeService).getAll();
    }

    @Test
    public void getEmployeeTest() throws Exception{
        when(employeeService.get("123")).thenReturn(new Employee("123", "Suti", "SCM"));
        mockMvc.perform(get("/get/123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is("123")))
                .andExpect(jsonPath("$.name", Matchers.is("Suti")))
                .andExpect(jsonPath("$.dept", Matchers.is("SCM")));
        verify(employeeService).get("123");
    }

    @Test
    public void addEmployeeTest() throws Exception{
        Employee employee = new Employee("123", "Suti", "SRM");
        when(employeeService.add(employee)).thenReturn(employee);

        String json = new ObjectMapper().writeValueAsString(employee);
        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is("123")))
                .andExpect(jsonPath("$.name", Matchers.is("Suti")))
                .andExpect(jsonPath("$.dept", Matchers.is("SRM")));

        verify(employeeService).add(employee);
    }
}
