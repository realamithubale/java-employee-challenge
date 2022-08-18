package com.example.rqchallenge.controller;

import com.example.rqchallenge.constants.EmployeeConstants;
import com.example.rqchallenge.employees.IEmployeeController;
import com.example.rqchallenge.exceptionhandler.EmployeeInputException;
import com.example.rqchallenge.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.example.rqchallenge.dto.Employee;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class EmployeeController implements IEmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Override
	public ResponseEntity<List<Employee>> getAllEmployees() throws URISyntaxException {
		log.debug("Rest request to get all employee");
		return ResponseEntity.ok(employeeService.getAllEmployee());
	}

	@Override
	public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) throws URISyntaxException {
		log.debug("Rest request to search employee by name, search String : {}", searchString);
		return ResponseEntity.ok(employeeService.getEmployeesByNameSearch(searchString));
	}

	@Override
	public ResponseEntity<Employee> getEmployeeById(String id) throws URISyntaxException {
		log.debug("Rest request to get employee by id, id {}", id);
		return ResponseEntity.ok(employeeService.getEmployeeById(id));
	}

	@Override
	public ResponseEntity<Integer> getHighestSalaryOfEmployees() throws URISyntaxException {
		log.debug("Rest request to get highest salary of employees");
		return ResponseEntity.ok(employeeService.getHighestSalaryOfEmployees());
	}

	@Override
	public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() throws URISyntaxException {
		log.debug("Rest request to get top ten highest earning employee names");
		return ResponseEntity.ok(employeeService.getTopTenHighestEarningEmployeeNames());
	}

	@Override
	public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) throws URISyntaxException {
		log.debug("Rest request to create employee, input data {}", employeeInput);
		Employee employee = new Employee();
		try {
			if (null != employeeInput.get("employee_name"))
				employee.setEmployee_name(employeeInput.get("employee_name").toString());
			if (null != employeeInput.get("employee_age"))
				employee.setEmployee_age(Integer.parseInt(employeeInput.get("employee_age").toString()));
			if (null != employeeInput.get("employee_salary"))
				employee.setEmployee_salary(Integer.parseInt(employeeInput.get("employee_salary").toString()));
		} catch (Exception e) {
			throw new EmployeeInputException(EmployeeConstants.ERROR_EMPLOYEE_INPUT_MESSAGE);
		}
		employee = employeeService.createEmployee(employee);
		return ResponseEntity.ok(employee);
	}

	@Override
	public ResponseEntity<String> deleteEmployeeById(String id) {
		log.debug("Rest request to delete employee by id : {}", id);
		String response = employeeService.deleteEmployee(id);
		return ResponseEntity.ok(response);
	}
}
