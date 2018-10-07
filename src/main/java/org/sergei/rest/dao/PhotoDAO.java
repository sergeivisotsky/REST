package org.sergei.rest.dao;

import org.hibernate.Session;
import org.sergei.rest.model.Photo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class PhotoDAO extends GenericHibernateDAO<Photo> {
    public PhotoDAO() {
        setPersistentClass(Photo.class);
    }

    public Photo findByCustomerNumberAndPhotoId(Long customerNumber, Long photoId) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select p from Photo p where p.customer.customerNumber = :customerNumber and p.photoId = :photoId");
        query.setParameter("customerNumber", customerNumber);
        query.setParameter("photoId", photoId);
        Photo photo = (Photo) query.getSingleResult();
        session.close();
        return photo;
    }

    public void deleteFileByCustomerNumberAndFileId(Photo photo) {
        super.delete(photo);
    }
}
