package org.sergei.rest.dao;

import org.hibernate.Session;
import org.sergei.rest.dao.generic.AbstractJpaHibernateDAO;
import org.sergei.rest.model.OrderDetails;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
@SuppressWarnings("unchecked")
public class OrderDetailsDAO extends AbstractJpaHibernateDAO<OrderDetails> {

    private static final String SQL_FIND_ALL_BY_ORDER_NUMBER = "SELECT o FROM OrderDetails o WHERE o.order.orderId = :orderId";

    public OrderDetailsDAO() {
        setPersistentClass(OrderDetails.class);
    }

    public List<OrderDetails> findAllByOrderId(Long orderId) {
        Query query = entityManager.createQuery(SQL_FIND_ALL_BY_ORDER_NUMBER);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
}
