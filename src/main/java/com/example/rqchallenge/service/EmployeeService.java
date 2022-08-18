package com.example.rqchallenge.service;

import com.example.rqchallenge.client.DummyRestAPIExampleClient;
import com.example.rqchallenge.constants.EmployeeConstants;
import com.example.rqchallenge.dto.Employee;
import com.example.rqchallenge.dto.EmployeeListResponse;
import com.example.rqchallenge.dto.EmployeeResponse;
import com.example.rqchallenge.exceptionhandler.ExternalServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {

	@Autowired
	private DummyRestAPIExampleClient restAPIExampleClient;

	public List<Employee> getAllEmployee() throws URISyntaxException {
		log.debug("Request for get all employee");
		EmployeeListResponse employeeListResponse = restAPIExampleClient.getAllEmployee();
		if (EmployeeConstants.SUCCESS.equals(employeeListResponse.getStatus())) {
			return restAPIExampleClient.getAllEmployee().getData();
		}
		throw new ExternalServiceException(employeeListResponse.getStatus());
	}

	public Employee getEmployeeById(String id) throws URISyntaxException {
		log.debug("Request for get employee by id");
		EmployeeResponse employee = restAPIExampleClient.getEmployeeById(id);
		if (EmployeeConstants.SUCCESS.equals(employee.getStatus())) {
			return employee.getData();
		}
		throw new ExternalServiceException(employee.getMessage());

	}

	public List<Employee> getEmployeesByNameSearch(String searchString) throws URISyntaxException {
		log.debug("Request for search employee by name");
		EmployeeListResponse employeeListResponse = restAPIExampleClient.getAllEmployee();
		if (EmployeeConstants.SUCCESS.equals(employeeListResponse.getStatus())) {
			List<Employee> employeeList = employeeListResponse.getData();
			return employeeList
					.stream()
					.filter(employee -> employee.getEmployee_name().contains(searchString))
					.collect(Collectors.toList());
		}
		throw new ExternalServiceException(employeeListResponse.getStatus());
	}

	public Integer getHighestSalaryOfEmployees() throws URISyntaxException {
		log.debug("Request for get highest salary of employees");
		EmployeeListResponse employeeListResponse = restAPIExampleClient.getAllEmployee();
		if (EmployeeConstants.SUCCESS.equals(employeeListResponse.getStatus())) {
			List<Employee> employeeList = employeeListResponse.getData();
			return employeeList.stream()
					.mapToInt(Employee::getEmployee_salary)
					.max()
					.orElse(0);
		}
		throw new ExternalServiceException(employeeListResponse.getStatus());
	}

	public List<String> getTopTenHighestEarningEmployeeNames() throws URISyntaxException {
		log.debug("Request for get top ten highest earning employee names");
		EmployeeListResponse employeeListResponse = restAPIExampleClient.getAllEmployee();
		if (EmployeeConstants.SUCCESS.equals(employeeListResponse.getStatus())) {
			List<Employee> employeeList = employeeListResponse.getData();
			return employeeList.stream()
					.sorted(Comparator.comparingLong(Employee::getEmployee_salary).reversed())
					.limit(10)
					.map(Employee::getEmployee_name)
					.collect(Collectors.toList());
		}
		throw new ExternalServiceException(employeeListResponse.getStatus());
	}

	public Employee createEmployee(Employee employee) throws URISyntaxException {
		log.debug("Request for create employee");
		EmployeeResponse createResponse = restAPIExampleClient.createEmployee(employee);
		if (EmployeeConstants.SUCCESS.equals(createResponse.getStatus())) {
			return createResponse.getData();
		}
		throw new ExternalServiceException(createResponse.getMessage());
	}

	public String deleteEmployee(String id) {
		log.debug("Request for delete employee");
		restAPIExampleClient.deleteEmployee(id);
		return EmployeeConstants.SUCCESS;
	}
}
