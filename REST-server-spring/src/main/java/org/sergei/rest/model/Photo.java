/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.rest.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "photos")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_id")
    @SequenceGenerator(name = "photo_id", sequenceName = "photo_id", allocationSize = 1)
    @Column(name = "photo_id")
    private Long photoId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    // TODO: ON DELETE CASCADE programmatically
    private Customer customer;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    public Photo() {
    }

    public Photo(Customer customer, String fileName, String fileUrl,
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
