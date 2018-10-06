package org.sergei.rest.model;

import java.io.Serializable;
import java.util.Objects;

class OrderDetailsId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Order order;
    private Product product;

    public OrderDetailsId() {
    }

    public OrderDetailsId(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetailsId)) return false;
        OrderDetailsId that = (OrderDetailsId) o;
        return Objects.equals(order, that.order) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}
