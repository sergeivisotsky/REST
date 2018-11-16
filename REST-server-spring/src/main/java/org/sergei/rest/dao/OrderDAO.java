package org.sergei.rest.dao;

import org.hibernate.Session;
import org.sergei.rest.dao.generic.GenericHibernateDAO;
import org.sergei.rest.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
@SuppressWarnings("unchecked")
public class OrderDAO extends GenericHibernateDAO<Order> {

    private static final String SQL_FIND_ALL_BY_CUSTOMER_NUMBER = "SELECT o FROM Order o WHERE o.customer.customerId = :customerId";

    private static final String SQL_FIND_ALL_BY_PRODUCT_CODE = "SELECT o FROM Order o INNER JOIN OrderDetails od ON " +
            "o.orderId = od.order.orderId WHERE od.product.productCode = :productCode";

    public OrderDAO() {
        setPersistentClass(Order.class);
    }

    public List<Order> findAllByCustomerId(Long customerId) {
        Session session = sessionFactory.openSession();
        TypedQuery<Order> query = session.createQuery(SQL_FIND_ALL_BY_CUSTOMER_NUMBER);
        query.setParameter("customerId", customerId);
        List<Order> orders = query.getResultList();
        session.close();
        return orders;
    }

    public List<Order> findAllByProductCode(String productCode) {
        Session session = sessionFactory.openSession();
        TypedQuery<Order> query = session.createQuery(SQL_FIND_ALL_BY_PRODUCT_CODE);
        query.setParameter("productCode", productCode);
        List<Order> orders = query.getResultList();
        session.close();
        return orders;
    }
}
