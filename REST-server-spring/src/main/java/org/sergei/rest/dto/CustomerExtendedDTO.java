/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
public class CustomerExtendedDTO extends CustomerDTO {
    @Override
    public Long getCustomerId() {
        return super.getCustomerId();
    }

    private List<String> photos = new LinkedList<>();

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
