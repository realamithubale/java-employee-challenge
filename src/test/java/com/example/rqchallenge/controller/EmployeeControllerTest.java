package com.example.rqchallenge.controller;

import com.example.rqchallenge.dto.Employee;
import com.example.rqchallenge.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.doReturn;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeControllerTest {

	@Autowired
	private EmployeeController employeeController;

	@MockBean
	private EmployeeService employeeService;

	private List<Employee> employeeList;

	@BeforeEach
	void setUp() {
		employeeList = new ArrayList<>();
		employeeList.add(new Employee(1L, "Amit", 99999, 28, ""));
		employeeList.add(new Employee(2L, "Amita", 1234, 27, ""));
	}

	@Test
	void getAllEmployees() throws URISyntaxException {
		doReturn(employeeList).when(employeeService).getAllEmployee();
		ResponseEntity<List<Employee>> employees = employeeController.getAllEmployees();
		assertEquals(HttpStatus.OK, employees.getStatusCode());
		assertEquals(2, employees.getBody().size());
	}

	@Test
	void getEmployeesByNameSearch() throws URISyntaxException {
		doReturn(employeeList).when(employeeService).getEmployeesByNameSearch("Am");
		ResponseEntity<List<Employee>> employees = employeeController.getEmployeesByNameSearch("Am");
		assertEquals(HttpStatus.OK, employees.getStatusCode());
		assertEquals(2, employees.getBody().size());
	}

	@Test
	void getEmployeeById() throws URISyntaxException {
		Employee employee = new Employee(1L, "Amit", 99999, 28, "");
		doReturn(employee).when(employeeService).getEmployeeById("1");
		ResponseEntity<Employee> employeeById = employeeController.getEmployeeById("1");
		assertEquals(HttpStatus.OK, employeeById.getStatusCode());
		assertEquals(employee, employeeById.getBody());
	}

	@Test
	void getHighestSalaryOfEmployees() throws URISyntaxException {
		doReturn(99999).when(employeeService).getHighestSalaryOfEmployees();
		ResponseEntity<Integer> salary = employeeController.getHighestSalaryOfEmployees();
		assertEquals(HttpStatus.OK, salary.getStatusCode());
		assertEquals(99999, salary.getBody());
	}
}
