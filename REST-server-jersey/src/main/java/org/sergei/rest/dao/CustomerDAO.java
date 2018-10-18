package org.sergei.rest.dao;

import org.sergei.rest.model.Customer;

@SuppressWarnings("unchecked")
public class CustomerDAO extends GenericHibernateDAO<Customer> {
    public CustomerDAO() {
        setPersistentClass(Customer.class);
    }
}
