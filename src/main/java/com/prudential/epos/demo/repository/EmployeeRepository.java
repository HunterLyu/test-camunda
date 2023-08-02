package com.prudential.epos.demo.repository;

import com.prudential.epos.demo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository to manage {@link Employee} instances.
 *
 * @author Oliver Gierke
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {


}