package org.sergei.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Photo", description = "All photo meta data")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
