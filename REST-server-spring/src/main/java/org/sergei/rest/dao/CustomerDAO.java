package org.sergei.rest.dao;

import org.sergei.rest.dao.generic.GenericHibernateDAO;
import org.sergei.rest.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAO extends GenericHibernateDAO<Customer> {
    public CustomerDAO() {
        setPersistentClass(Customer.class);
    }
}