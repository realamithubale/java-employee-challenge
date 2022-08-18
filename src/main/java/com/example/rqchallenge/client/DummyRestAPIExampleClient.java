package com.example.rqchallenge.client;

import com.example.rqchallenge.dto.Employee;
import com.example.rqchallenge.dto.EmployeeListResponse;
import com.example.rqchallenge.dto.EmployeeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@Slf4j
public class DummyRestAPIExampleClient {

	@Value("${employee.app.base.url}")
	String baseURL;

	@Autowired
	private RestTemplate restTemplate;

	public EmployeeListResponse getAllEmployee() throws URISyntaxException {
		URI uri = new URI(baseURL);
		ResponseEntity<EmployeeListResponse> response = restTemplate.getForEntity(uri, EmployeeListResponse.class);
		return response.getBody();

	}

	public EmployeeResponse getEmployeeById(String id) throws URISyntaxException {
		URI uri = new URI(baseURL + id);
		ResponseEntity<EmployeeResponse> response = restTemplate.getForEntity(uri, EmployeeResponse.class);
		return response.getBody();
	}

	public EmployeeResponse createEmployee(Employee employee) throws URISyntaxException {
		URI uri = new URI(String.format("%screate", baseURL));
		ResponseEntity<EmployeeResponse> response = restTemplate.postForEntity(uri, employee, EmployeeResponse.class);
		return response.getBody();
	}

	public void deleteEmployee(String id) {
		restTemplate.delete(String.format("%s/delete/%s", baseURL, id));
	}

}
