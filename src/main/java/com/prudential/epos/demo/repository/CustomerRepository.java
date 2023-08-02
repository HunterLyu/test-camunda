package com.prudential.epos.demo.repository;

import com.prudential.epos.demo.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository to manage {@link Customer} instances.
 *
 * @author Oliver Gierke
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

	/**
	 * Returns a page of {@link Customer}s with the given lastname.
	 *
	 * @param lastName
	 * @param pageable
	 * @return
	 */
	Page<Customer> findByLastName(String lastName, Pageable pageable);
}