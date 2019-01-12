/*
 * Copyright 2018-2019 Sergei Visotsky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
