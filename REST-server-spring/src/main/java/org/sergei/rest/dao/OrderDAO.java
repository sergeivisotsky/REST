package org.sergei.rest.dao;

import org.sergei.rest.dao.generic.AbstractJpaHibernateDAO;
import org.sergei.rest.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
@SuppressWarnings("unchecked")
public class OrderDAO extends AbstractJpaHibernateDAO<Order> {

    private static final String SQL_FIND_ALL_BY_CUSTOMER_ID = "SELECT o FROM Order o WHERE o.customer.customerId = :customerId";

    private static final String SQL_FIND_ALL_BY_PRODUCT_CODE = "SELECT o FROM Order o INNER JOIN OrderDetails od ON " +
            "o.orderId = od.order.orderId WHERE od.product.productCode = :productCode";

    public OrderDAO() {
        setPersistentClass(Order.class);
    }

    public List<Order> findAllByCustomerId(Long customerId) {
        Query query = entityManager.createQuery(SQL_FIND_ALL_BY_CUSTOMER_ID);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    public List<Order> findAllByProductCode(String productCode) {
        Query query = entityManager.createQuery(SQL_FIND_ALL_BY_PRODUCT_CODE);
        query.setParameter("productCode", productCode);
        return query.getResultList();
    }
}
