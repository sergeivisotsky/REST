package org.sergei.rest.dao;

import org.hibernate.Session;
import org.sergei.rest.dao.generic.AbstractJpaHibernateDAO;
import org.sergei.rest.model.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
@SuppressWarnings("unchecked")
public class ProductDAO extends AbstractJpaHibernateDAO<Product> {
    public ProductDAO() {
        setPersistentClass(Product.class);
    }

    public Product findByCode(String productCode) {
        Query query = entityManager.createQuery("SELECT p FROM Product p  WHERE p.product_code = :productCode");
        query.setParameter("productCode", productCode);
        return (Product) query.getSingleResult();
    }
}
