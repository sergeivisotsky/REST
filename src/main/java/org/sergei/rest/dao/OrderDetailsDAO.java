package org.sergei.rest.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.sergei.rest.model.OrderDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class OrderDetailsDAO extends GenericHibernateDAO<OrderDetails> {
    public OrderDetailsDAO() {
        setPersistentClass(OrderDetails.class);
    }

    public List<OrderDetails> findAllByOrderNumber(Long orderNumber) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT o FROM OrderDetails o WHERE o.order.orderNumber = :orderNumber");
        query.setParameter("orderNumber", orderNumber);
        List<OrderDetails> orderDetails = query.list();
        session.close();
        return orderDetails;
    }
}
