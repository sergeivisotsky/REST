package org.sergei.rest.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.sergei.rest.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class OrderDAO extends GenericHibernateDAO<Order> {
    public OrderDAO() {
        setPersistentClass(Order.class);
    }

    public List<Order> findAllByCustomerNumber(Long customerNumber) {
        Session session = sessionFactory.openSession();
        /*Query query = session.createQuery("select o from Order o where o.customer.customerNumber = :customerNumber");
        query.setParameter("customerNumber", customerNumber);*/
        Criteria criteria = (Criteria) session.createQuery("select o from Order o where o.customer.customerNumber = :customerNumber");
        List<Order> orders = criteria.list();
        session.close();
        return orders;
    }
}
