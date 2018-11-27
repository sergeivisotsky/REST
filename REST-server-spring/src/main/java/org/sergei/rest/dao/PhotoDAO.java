package org.sergei.rest.dao;

import org.sergei.rest.dao.generic.AbstractJpaHibernateDAO;
import org.sergei.rest.model.Photo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
@SuppressWarnings("unchecked")
public class PhotoDAO extends AbstractJpaHibernateDAO<Photo> {

    private static final String SQL_FIND_BY_CUSTOMER_NUMBER_AND_PHOTO_ID = "SELECT p FROM Photo p WHERE p.customer.customerId = :customerId AND p.photoId = :photoId";

    private static final String SQL_FIND_ALL_BY_PHOTO_AND_CUSTOMER_NUMBER = "SELECT p FROM Photo p WHERE p.customer.customerId = :customerId";

    private static final String SQL_FIND_ALL_BY_CUSTOMER_NUMBER_AND_FILE_NAME = "SELECT p FROM Photo p WHERE p.customer.customerId = :customerId and p.fileName = :fileName";

    public PhotoDAO() {
        setPersistentClass(Photo.class);
    }

    public List<Photo> findAllPhotosByCustomerId(Long customerId) {
        Query query = entityManager.createQuery(SQL_FIND_ALL_BY_PHOTO_AND_CUSTOMER_NUMBER);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    public Photo findPhotoByCustomerIdAndFileName(Long customerId, String fileName) {
        Query query = entityManager.createQuery(SQL_FIND_ALL_BY_CUSTOMER_NUMBER_AND_FILE_NAME);
        query.setParameter("customerId", customerId);
        query.setParameter("fileName", fileName);
        return (Photo) query.getSingleResult();
    }

    public Photo findByCustomerIdAndPhotoId(Long customerId, Long photoId) {
        Query query = entityManager.createQuery(SQL_FIND_BY_CUSTOMER_NUMBER_AND_PHOTO_ID);
        query.setParameter("customerId", customerId);
        query.setParameter("photoId", photoId);
        return (Photo) query.getSingleResult();
    }
}
