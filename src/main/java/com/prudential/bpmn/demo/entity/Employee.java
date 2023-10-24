package com.prudential.bpmn.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Oliver Gierke
 */
@Entity
@Slf4j
@Data
@Builder
public class Employee {
	@Id
	private String id;
	private String name;
 	private int remainingHolidays;
}