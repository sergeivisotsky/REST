package org.sergei.rest.dao;

import org.sergei.rest.dao.generic.AbstractJpaHibernateDAO;
import org.sergei.rest.model.Customer;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public class CustomerDAO extends AbstractJpaHibernateDAO<Customer> {
    public CustomerDAO() {
        setPersistentClass(Customer.class);
    }
}