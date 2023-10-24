package com.prudential.bpmn.demo.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.prudential.bpmn.demo.entity.Employee;


/**
 * Repository to manage {@link Employee} instances.
 *
 * @author Oliver Gierke
 */
public interface EmployeeRepository extends CrudRepository<Employee, String>, JpaSpecificationExecutor<Employee> {


}