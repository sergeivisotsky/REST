package org.sergei.rest.service;

import org.sergei.rest.exceptions.RecordNotFoundException;
import org.sergei.rest.model.Customer;
import org.sergei.rest.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    /*private final OrderRepository orderRepository;

    private final OrderDetailsRepository orderDetailsRepository;*/

    @Autowired
    public CustomerService(CustomerRepository customerRepository/*,
                           OrderRepository orderRepository,
                           OrderDetailsRepository orderDetailsRepository*/) {
        this.customerRepository = customerRepository;
        /*this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;*/
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerByNumber(Long customerNumber) {
        return customerRepository.findById(customerNumber)
                .orElseThrow(() -> new RecordNotFoundException("Customer with this number not found"));
        /*List<Order> orders = orderRepository.findAllByCustomerNumber(customerNumber)
                .orElseThrow(() -> new RecordNotFoundException("No orders for this customer found"));
        Order order = new Order();*/
//        List<OrderDetails> orderDetails = orderDetailsRepository.findAllByOrderNumber(order.getOrderNumber());
//        orders.forEach(order1 -> order.setOrderDetails(orderDetailsRepository.findAllByOrderNumber(order.getOrderNumber())));
//        customer.setOrders(orders);

//        return customer;
    }

    // Save customer
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    // Update customer by customer number
    public Customer updateCustomer(Long customerNumber, Customer customerRequest) {
        return customerRepository.findById(customerNumber)
                .map(customer -> {
                    customer.setCustomerNumber(customerRequest.getCustomerNumber());
                    customer.setFirstName(customerRequest.getFirstName());
                    customer.setLastName(customerRequest.getLastName());
                    customer.setAge(customerRequest.getAge());
                    customer.setOrders(customerRequest.getOrders());
                    return customerRepository.save(customer);
                }).orElseThrow(() -> new RecordNotFoundException("Customer with this number not found"));
    }

    // Delete customer by number
    public Customer deleteCustomerByNumber(Long customerNumber) {
        return customerRepository.findById(customerNumber).map(customer -> {
            customerRepository.delete(customer);
            return customer;
        }).orElseThrow(() -> new RecordNotFoundException("Customer with this number not found"));
    }
}
