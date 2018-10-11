package org.sergei.rest.dao;

import org.hibernate.Session;
import org.sergei.rest.model.Photo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class PhotoDAO extends GenericHibernateDAO<Photo> {

    private static final String SQL_FIND_BY_CUSTOMER_NUMBER_AND_PHOTO_ID = "SELECT p FROM Photo p WHERE p.customer.customerNumber = :customerNumber AND p.photoId = :photoId";

    private static final String SQL_FIND_ALL_BY_PHOTO_AND_CUSTOMER_NUMBER = "SELECT p FROM Photo p WHERE p.customer.customerNumber = :customerNumber";

    private static final String SQL_FIND_ALL_BY_CUSTOMER_NUMBER_AND_FILE_NAME = "SELECT p FROM Photo p WHERE p.customer.customerNumber = :customerNumber and p.fileName = :fileName";

    public PhotoDAO() {
        setPersistentClass(Photo.class);
    }

    public Photo findByCustomerNumberAndPhotoId(Long customerNumber, Long photoId) {
        Session session = sessionFactory.openSession();
        TypedQuery<Photo> query = session.createQuery(SQL_FIND_BY_CUSTOMER_NUMBER_AND_PHOTO_ID);
        query.setParameter("customerNumber", customerNumber);
        query.setParameter("photoId", photoId);
        Photo photo = query.getSingleResult();
        session.close();
        return photo;
    }

    public List<Photo> findAllPhotosByCustomerNumber(Long customerNumber) {
        Session session = sessionFactory.openSession();
        TypedQuery<Photo> query = session.createQuery(SQL_FIND_ALL_BY_PHOTO_AND_CUSTOMER_NUMBER);
        query.setParameter("customerNumber", customerNumber);
        List<Photo> photos = query.getResultList();
        session.close();
        return photos;
    }

    public Photo findPhotoByCustomerNumberAndFileName(Long customerNumber, String fileName) {
        Session session = sessionFactory.openSession();
        TypedQuery<Photo> query = session.createQuery(SQL_FIND_ALL_BY_CUSTOMER_NUMBER_AND_FILE_NAME);
        query.setParameter("customerNumber", customerNumber);
        query.setParameter("fileName", fileName);
        Photo photo = query.getSingleResult();
        session.close();
        return photo;
    }
}
