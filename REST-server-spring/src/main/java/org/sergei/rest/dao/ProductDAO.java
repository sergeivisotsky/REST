/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.rest.dao;

import org.hibernate.Session;
import org.sergei.rest.dao.generic.GenericHibernateDAO;
import org.sergei.rest.model.Product;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ProductDAO extends GenericHibernateDAO<Product> {
    public ProductDAO() {
        setPersistentClass(Product.class);
    }

    public Product findByCode(String productCode) {
        Session session = sessionFactory.openSession();
        Product product = session.get(Product.class, productCode);
        session.close();
        return product;
    }
}
