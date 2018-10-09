package org.sergei.rest.dao;

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
        Query query = session.createQuery("SELECT o FROM Order o WHERE o.customer.customerNumber = :customerNumber");
        query.setParameter("customerNumber", customerNumber);
        List<Order> orders = query.list();
        session.close();
        return orders;
    }

    public List<Order> findAllByProductCode(String productCode) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT o FROM Order o INNER JOIN OrderDetails od ON " +
                "o.orderNumber = od.order.orderNumber WHERE od.product.productCode = :productCode");
        query.setParameter("productCode", productCode);
        List<Order> orders = query.list();
        session.close();
        return orders;
    }
}
