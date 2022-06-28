package io.davidarchanjo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.davidarchanjo.model.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
