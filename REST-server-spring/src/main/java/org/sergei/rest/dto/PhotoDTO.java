package org.sergei.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiModel(value = "Photo", description = "All photo meta data")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PhotoDTO {
    @XmlElement
    private Long photoId;

    @XmlElement
    private Long customerId;

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

    public PhotoDTO(Long photoId, Long customerId, String fileName,
                    String fileUrl, String fileType, Long fileSize) {
        this.photoId = photoId;
        this.customerId = customerId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public Long getPhotoId() {
        return photoId;
    }

    @ApiModelProperty(hidden = true, readOnly = true)
    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
