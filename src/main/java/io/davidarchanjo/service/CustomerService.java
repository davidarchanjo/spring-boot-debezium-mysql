package io.davidarchanjo.service;

import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.davidarchanjo.model.domain.Customer;
import io.davidarchanjo.repository.CustomerRepository;
import io.debezium.data.Envelope.Operation;
import lombok.RequiredArgsConstructor;

@Service
@Profile("db")
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void replicateData(Map<String, Object> customerData, Operation operation) {
        final ObjectMapper mapper = new ObjectMapper();
        final Customer customer = mapper.convertValue(customerData, Customer.class);

        if (Operation.DELETE == operation) {
            customerRepository.deleteById(customer.getId());
        } else {
            customerRepository.save(customer);
        }
    }
}