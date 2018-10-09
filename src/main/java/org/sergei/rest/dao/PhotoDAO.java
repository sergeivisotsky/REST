package org.sergei.rest.dao;

import org.hibernate.Session;
import org.sergei.rest.model.Photo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class PhotoDAO extends GenericHibernateDAO<Photo> {
    public PhotoDAO() {
        setPersistentClass(Photo.class);
    }

    public Photo findByCustomerNumberAndPhotoId(Long customerNumber, Long photoId) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT p FROM Photo p WHERE p.customer.customerNumber = :customerNumber AND p.photoId = :photoId");
        query.setParameter("customerNumber", customerNumber);
        query.setParameter("photoId", photoId);
        Photo photo = (Photo) query.getSingleResult();
        session.close();
        return photo;
    }

    public List<Photo> findAllPhotosByCustomerNumber(Long customerNumber) {
        Session session = sessionFactory.openSession();
        org.hibernate.Query query = session.createQuery("SELECT p FROM Photo p WHERE p.customer.customerNumber = :customerNumber");
        query.setParameter("customerNumber", customerNumber);
        List<Photo> photos = query.list();
        session.close();
        return photos;
    }

    public Photo findPhotoByCustomerNumberAndFileName(Long customerNumber, String fileName) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select p from Photo p where p.customer.customerNumber = :customerNumber and p.fileName = :fileName");
        query.setParameter("customerNumber", customerNumber);
        query.setParameter("fileName", fileName);
        Photo photo = (Photo) query.getSingleResult();
        session.close();
        return photo;
    }
}
