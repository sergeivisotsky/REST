package org.sergei.rest.dto;

import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(value = "Photo", description = "All photo meta")
public class PhotoDTO {
    @XmlElement
    private Long photoId;

    @XmlElement
    private Long customerNumber;

    @XmlElement
    private String fileName;

    @XmlElement
    private String fileUrl;

    @XmlElement
    private String fileType;

    @XmlElement
    private Long fileSize;

    public PhotoDTO() {
    }

    public PhotoDTO(Long photoId, Long customerNumber, String fileName,
                    String fileUrl, String fileType, Long fileSize) {
        this.photoId = photoId;
        this.customerNumber = customerNumber;
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

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
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