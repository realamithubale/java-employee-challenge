package com.example.rqchallenge.service;

import com.example.rqchallenge.client.DummyRestAPIExampleClient;
import com.example.rqchallenge.constants.EmployeeConstants;
import com.example.rqchallenge.dto.Employee;
import com.example.rqchallenge.dto.EmployeeListResponse;
import com.example.rqchallenge.dto.EmployeeResponse;
import com.example.rqchallenge.exceptionhandler.ExternalServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.doReturn;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceTest {

	public static final int HIGHEST_SALARY = 34435556;
	public static final int TOTAL_EMPLOYEE = 16;
	@Autowired
	private EmployeeService employeeService;

	@MockBean
	private DummyRestAPIExampleClient restAPIExampleClient;

	private EmployeeListResponse employeeListResponse;

	@BeforeEach
	void setUp() {
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(new Employee(1L, "AA", 99999, 28, ""));
		employeeList.add(new Employee(2L, "BB", 234, 27, ""));
		employeeList.add(new Employee(3L, "CC", 457657, 27, ""));
		employeeList.add(new Employee(4L, "CC", 5675687, 27, ""));
		employeeList.add(new Employee(5L, "DD", 4567, 27, ""));
		employeeList.add(new Employee(6L, "EE", 456, 27, ""));
		employeeList.add(new Employee(7L, "FF", 3556, 27, ""));
		employeeList.add(new Employee(8L, "GG", 6788, 27, ""));
		employeeList.add(new Employee(9L, "HH", 6788, 27, ""));
		employeeList.add(new Employee(10L, "II", 12345, 27, ""));
		employeeList.add(new Employee(11L, "JJ", 98765, 27, ""));
		employeeList.add(new Employee(12L, "KK", 345456, 29, ""));
		employeeList.add(new Employee(13L, "LL", HIGHEST_SALARY, 29, ""));
		employeeList.add(new Employee(14L, "MM", 3453456, 29, ""));
		employeeList.add(new Employee(15L, "NN", 23443654, 26, ""));
		employeeList.add(new Employee(16L, "OO", 456, 28, ""));
		employeeListResponse = new EmployeeListResponse(employeeList, EmployeeConstants.SUCCESS);
	}

	@Test
	void getAllEmployee() throws URISyntaxException {
		doReturn(employeeListResponse).when(restAPIExampleClient).getAllEmployee();
		List<Employee> res = employeeService.getAllEmployee();
		assertEquals(TOTAL_EMPLOYEE, res.size());

	}

	@Test()
	void getAllEmployeeFailed() throws URISyntaxException {
		employeeListResponse.setStatus("Failed");
		doReturn(employeeListResponse).when(restAPIExampleClient).getAllEmployee();
		Assertions.assertThrows(ExternalServiceException.class, () -> employeeService.getAllEmployee());
	}

	@Test
	void getEmployeeById() throws URISyntaxException {
		Employee aa = new Employee(1L, "AA", 99999, 28, "");
		EmployeeResponse employeeResponse = new EmployeeResponse(aa, EmployeeConstants.SUCCESS, "message");
		doReturn(employeeResponse).when(restAPIExampleClient).getEmployeeById("1");
		Employee res = employeeService.getEmployeeById("1");
		assertEquals(aa, res);
	}

	@Test
	void getEmployeeByIdFailed() throws URISyntaxException {
		Employee aa = new Employee(1L, "AA", 99999, 28, "");
		EmployeeResponse employeeResponse = new EmployeeResponse(aa, "Failed", "message");
		doReturn(employeeResponse).when(restAPIExampleClient).getEmployeeById("1");
		Assertions.assertThrows(ExternalServiceException.class, () -> employeeService.getEmployeeById("1"));
	}

	@Test
	void getEmployeesByNameSearch() throws URISyntaxException {
		doReturn(employeeListResponse).when(restAPIExampleClient).getAllEmployee();
		List<Employee> res = employeeService.getEmployeesByNameSearch("AA");
		assertEquals(1, res.size());
		assertEquals("AA", res.get(0).getEmployee_name());
	}

	@Test
	void getstSalaryOfEmployees() throws URISyntaxException {
		doReturn(employeeListResponse).when(restAPIExampleClient).getAllEmployee();
		Integer res = employeeService.getHighestSalaryOfEmployees();
		assertEquals(HIGHEST_SALARY, res);
	}

	@Test
	void getTopTenHighestEarningEmployeeNames() throws URISyntaxException {
		doReturn(employeeListResponse).when(restAPIExampleClient).getAllEmployee();
		List<String> res = employeeService.getTopTenHighestEarningEmployeeNames();
		assertEquals(10, res.size());
	}
}
