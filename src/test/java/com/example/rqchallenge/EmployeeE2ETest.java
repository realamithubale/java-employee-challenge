package com.example.rqchallenge;

import com.example.rqchallenge.constants.EmployeeConstants;
import com.example.rqchallenge.dto.Employee;
import com.example.rqchallenge.dto.EmployeeListResponse;
import com.example.rqchallenge.dto.EmployeeResponse;
import com.example.rqchallenge.employees.IEmployeeController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@ActiveProfiles("test")
public class EmployeeE2ETest {

	@Autowired
	private IEmployeeController employeeController;

	@Autowired
	private RestTemplate restTemplate;

	private MockRestServiceServer mockServer;
	private ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	public void init() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	private Employee createEmployeeEric() {
		return new Employee(1L, "Eric Simmons", 10000, 27, "");
	}

	private Employee createEmployeeAmit() {
		return new Employee(2L, "Amit Hubale", 20000, 28, "");
	}

	private List<Employee> createEmployeeList() {
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(createEmployeeEric());
		employeeList.add(createEmployeeAmit());
		return employeeList;
	}

	@Test
	public void testGetEmployeeById() throws URISyntaxException, JsonProcessingException {
		Employee emp = createEmployeeEric();
		EmployeeResponse employeeResponse = new EmployeeResponse(emp, EmployeeConstants.SUCCESS, "message");
		mockServer.expect(ExpectedCount.once(),
						requestTo(new URI("https://localhost:8080/api/v1/employees/1")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(mapper.writeValueAsString(employeeResponse))
				);

		ResponseEntity<Employee> employee = employeeController.getEmployeeById("1");
		mockServer.verify();
		Assertions.assertEquals(emp, employee.getBody());
	}

	@Test
	public void testGetEmployeeByNameSearch() throws URISyntaxException, JsonProcessingException {
		EmployeeListResponse employeeResponse = new EmployeeListResponse(createEmployeeList(), EmployeeConstants.SUCCESS);
		mockServer.expect(ExpectedCount.once(),
						requestTo(new URI("https://localhost:8080/api/v1/employees/")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(mapper.writeValueAsString(employeeResponse))
				);

		ResponseEntity<List<Employee>> employeeListRes = employeeController.getEmployeesByNameSearch("Amit");
		mockServer.verify();
		Assertions.assertEquals(1, employeeListRes.getBody().size());
		Assertions.assertEquals(createEmployeeAmit(), employeeListRes.getBody().get(0));
	}

	@Test
	public void testGetHighestSalaryOfEmployees() throws URISyntaxException, JsonProcessingException {
		EmployeeListResponse employeeResponse = new EmployeeListResponse(createEmployeeList(), EmployeeConstants.SUCCESS);
		mockServer.expect(ExpectedCount.once(),
						requestTo(new URI("https://localhost:8080/api/v1/employees/")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(mapper.writeValueAsString(employeeResponse))
				);

		ResponseEntity<Integer> res = employeeController.getHighestSalaryOfEmployees();
		mockServer.verify();
		Assertions.assertEquals(createEmployeeAmit().getEmployee_salary(), res.getBody());
	}

	@Test
	public void testGetTopTenHighestEarningEmployeeNames() throws URISyntaxException, JsonProcessingException {
		EmployeeListResponse employeeResponse = new EmployeeListResponse(createEmployeeList(), EmployeeConstants.SUCCESS);
		mockServer.expect(ExpectedCount.once(),
						requestTo(new URI("https://localhost:8080/api/v1/employees/")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(mapper.writeValueAsString(employeeResponse))
				);

		ResponseEntity<List<String>> res = employeeController.getTopTenHighestEarningEmployeeNames();
		mockServer.verify();
		Assertions.assertEquals(2, res.getBody().size());
	}

	@Test
	public void testCreateEmployeeNames() throws URISyntaxException, JsonProcessingException {
		Employee emp = createEmployeeEric();
		EmployeeResponse employeeResponse = new EmployeeResponse(emp, EmployeeConstants.SUCCESS, "message");
		mockServer.expect(ExpectedCount.once(),
						requestTo(new URI("https://localhost:8080/api/v1/employees/create")))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(mapper.writeValueAsString(employeeResponse))
				);

		Map<String, Object> map = new HashMap<>();
		map.put("employee_name", "Eric Simmons");
		map.put("employee_age", 10000);
		map.put("employee_salary", 27);
		ResponseEntity<Employee> res = employeeController.createEmployee(map);
		mockServer.verify();
		Assertions.assertEquals(emp, res.getBody());
	}

	@Test
	public void testDeleteEmployeeNames() throws URISyntaxException, JsonProcessingException {
		Employee emp = createEmployeeEric();
		EmployeeResponse employeeResponse = new EmployeeResponse(emp, EmployeeConstants.SUCCESS, "message");
		mockServer.expect(ExpectedCount.once(),
						requestTo(new URI("https://localhost:8080/api/v1/employees/delete/1")))
				.andExpect(method(HttpMethod.DELETE))
				.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(mapper.writeValueAsString(employeeResponse))
				);

		ResponseEntity<String> res = employeeController.deleteEmployeeById("1");
		mockServer.verify();
		Assertions.assertEquals(EmployeeConstants.SUCCESS, res.getBody());
	}

}
