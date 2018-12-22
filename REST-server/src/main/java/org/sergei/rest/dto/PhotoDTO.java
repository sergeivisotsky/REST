package org.sergei.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Photo", description = "All photo meta data")
public class PhotoDTO {

    @ApiModelProperty("Photo ID")
    private Long photoId;

    @ApiModelProperty("Customer ID whose photo was uploaded")
    private Long customerId;

    @ApiModelProperty("Photo file name")
    private String fileName;

    @ApiModelProperty("Photo file URL")
    private String fileUrl;

    @ApiModelProperty("Photo file type")
    private String fileType;

    @ApiModelProperty("Photo file size")
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

    @ApiModelProperty(hidden = true)
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
