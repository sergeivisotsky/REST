package org.sergei.rest.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "photos")
public class PhotoUploadResponse {
    @XmlElement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long photoId;

    @XmlElement
    @Column(name = "customer_number")
    private Customer customer;

    @XmlElement
    @Column(name = "file_name")
    private String fileName;

    @XmlElement
    @Column(name = "file_url")
    private String fileUrl;

    @XmlElement
    @Column(name = "file_type")
    private String fileType;

    @XmlElement
    @Column(name = "file_size")
    private Long fileSize;

    public PhotoUploadResponse() {
    }

    public PhotoUploadResponse(Customer customer, String fileName, String fileUrl,
                               String fileType, Long fileSize) {
        this.customer = customer;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
