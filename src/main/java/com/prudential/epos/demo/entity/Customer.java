package com.prudential.epos.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Oliver Gierke
 */
@Entity
@Slf4j
public class Customer {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String firstname;
	private String lastname;

	public Long getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}
}