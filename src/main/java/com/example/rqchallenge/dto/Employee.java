package com.example.rqchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	private Long id;
	private String employee_name;
	private Integer employee_salary;
	private Integer employee_age;
	private String profileImage;
}
