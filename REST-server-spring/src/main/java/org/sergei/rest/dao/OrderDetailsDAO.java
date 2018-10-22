package org.sergei.rest.dao;

import org.hibernate.Session;
import org.sergei.rest.model.OrderDetails;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class OrderDetailsDAO extends GenericHibernateDAO<OrderDetails> {

    private static final String SQL_FIND_ALL_BY_ORDER_NUMBER = "SELECT o FROM OrderDetails o WHERE o.order.orderId = :orderId";

    public OrderDetailsDAO() {
        setPersistentClass(OrderDetails.class);
    }

    public List<OrderDetails> findAllByOrderId(Long orderId) {
        Session session = sessionFactory.openSession();
        TypedQuery<OrderDetails> query = session.createQuery(SQL_FIND_ALL_BY_ORDER_NUMBER);
        query.setParameter("orderId", orderId);
        List<OrderDetails> orderDetails = query.getResultList();
        session.close();
        return orderDetails;
    }
}
