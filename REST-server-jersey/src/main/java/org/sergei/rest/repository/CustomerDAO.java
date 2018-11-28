package org.sergei.rest.repository;

import org.sergei.rest.model.Customer;

/**
 * @author Sergei Visotsky, 2018
 */
@SuppressWarnings("unchecked")
public class CustomerDAO extends GenericHibernateDAO<Customer> {
    public CustomerDAO() {
        setPersistentClass(Customer.class);
    }
}
