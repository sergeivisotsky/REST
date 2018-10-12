package org.sergei.rest.dao;

import org.hibernate.Session;
import org.sergei.rest.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class OrderDAO extends GenericHibernateDAO<Order> {

    private static final String SQL_FIND_ALL_BY_CUSTOMER_NUMBER = "SELECT o FROM Order o WHERE o.customer.customerNumber = :customerNumber";

    private static final String SQL_FIND_ALL_BY_PRODUCT_CODE = "SELECT o FROM Order o INNER JOIN OrderDetails od ON " +
            "o.orderNumber = od.order.orderNumber WHERE od.product.productCode = :productCode";

    public OrderDAO() {
        setPersistentClass(Order.class);
    }

    public List<Order> findAllByCustomerNumber(Long customerNumber) {
        Session session = sessionFactory.openSession();
        TypedQuery<Order> query = session.createQuery(SQL_FIND_ALL_BY_CUSTOMER_NUMBER);
        query.setParameter("customerNumber", customerNumber);
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

    /*public Order findByCustomerNumberAndOrderNumber(Long customerNumber, Long orderNumber) {
        Session session = sessionFactory.openSession();
        TypedQuery<Order> query = session.createQuery("SELECT o FROM Order o WHERE o.customer.customerNumber = :customerNumber AND o.orderNumber = :orderNumber");
        query.setParameter("customerNumber", customerNumber);
        query.setParameter("orderNumber", orderNumber);
        Order order = query.getSingleResult();
        session.close();
        return order;
    }*/
}
