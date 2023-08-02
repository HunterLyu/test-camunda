package com.prudential.epos.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Oliver Gierke
 */
@Entity
@Slf4j
@Getter
@Setter
public class Employee {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
 	private int remainingHolidays;
}