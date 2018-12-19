package org.sergei.rest.controller.util;

import org.sergei.rest.controller.CustomerController;
import org.sergei.rest.controller.OrderController;
import org.sergei.rest.controller.PhotoController;
import org.sergei.rest.controller.v2.CustomerControllerV2;
import org.sergei.rest.controller.v2.OrderControllerV2;
import org.sergei.rest.controller.v2.ProductControllerV2;
import org.sergei.rest.dto.v2.CustomerDTOV2;
import org.sergei.rest.dto.v2.OrderDTOV2;
import org.sergei.rest.dto.v2.ProductDTOV2;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author Sergei Visotsky
 * Created on 12/18/2018
 */
public final class LinkUtil {

    /**
     * Hide from public use
     */
    private LinkUtil() {
    }

    /**
     * Set HATEOAS links for all customer
     *
     * @param customers collection of customer entities
     * @return resource with link set
     */
    public static Resources setLinksForAllCustomers(Iterable<CustomerDTOV2> customers) {
        customers.forEach(customer -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(CustomerControllerV2.class)
                            .getCustomerByIdV2(customer.getCustomerId())).withSelfRel();
            Link ordersLink = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(OrderController.class)
                            .getOrdersByCustomerId(customer.getCustomerId())).withRel("orders");
            Link photoLink = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(PhotoController.class)
                            .findAllCustomerPhotos(customer.getCustomerId())).withRel("photos");
            customer.add(link);
            customer.add(ordersLink);
            customer.add(photoLink);
        });
        return setServletResourceLinks(customers);
    }

    /**
     * Set HATEOAS links for one customer
     *
     * @param customerDTOV2 customer entity
     * @param customerId    customer ID for whom lnks to set
     * @return customer entity with links set
     */
    public static CustomerDTOV2 setLinksForCustomer(CustomerDTOV2 customerDTOV2, Long customerId) {
        Link link = ControllerLinkBuilder.linkTo(CustomerController.class).withSelfRel();
        Link ordersLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(OrderController.class)
                        .getOrdersByCustomerId(customerId)).withRel("orders");
        Link photoLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(PhotoController.class)
                        .findAllCustomerPhotos(customerDTOV2.getCustomerId())).withRel("photos");
        Link allCustomers = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(CustomerControllerV2.class)
                        .getAllCustomersV2()).withRel("allCustomers");
        customerDTOV2.add(link);
        customerDTOV2.add(ordersLink);
        customerDTOV2.add(photoLink);
        customerDTOV2.add(allCustomers);
        return customerDTOV2;
    }

    /**
     * Set HATEOAS links for order collection
     *
     * @param orders collection of orders
     * @return collection of orders with links set
     */
    public static Resources setLinksForAllOrders(Iterable<OrderDTOV2> orders) {
        orders.forEach(orderDTOV2 -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(OrderControllerV2.class)
                            .getOrderByCustomerAndOrderIdV2(orderDTOV2.getCustomerId(), orderDTOV2.getOrderId())).withSelfRel();

            orderDTOV2.add(link);
        });
        return setServletResourceLinks(orders);
    }

    /**
     * Set HATEOAS links for one order
     *
     * @param orderDTOV2 order entity
     * @return entity with links set
     */
    public static OrderDTOV2 setLinksForOneOrder(OrderDTOV2 orderDTOV2) {
        Link link = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(OrderControllerV2.class)
                        .getOrderByCustomerAndOrderIdV2(orderDTOV2.getCustomerId(), orderDTOV2.getOrderId())).withSelfRel();
        Link allOrdersLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(OrderControllerV2.class)
                        .getOrdersByCustomerIdV2(orderDTOV2.getCustomerId())).withRel("allCustomerOrders");
        orderDTOV2.add(link);
        orderDTOV2.add(allOrdersLink);
        return orderDTOV2;
    }

    public static Resources setLinksForAllProducts(Iterable<ProductDTOV2> products) {
        products.forEach(productDTOV2 -> {
            Link link = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(ProductControllerV2.class)
                            .getProductByCodeV2(productDTOV2.getProductCode())).withSelfRel();

            productDTOV2.add(link);
        });
        return setServletResourceLinks(products);
    }

    /**
     * Set HATEOAS links from servlet context
     *
     * @param <E>        Generic entity
     * @param collection collection of entities
     * @return resource with links set
     */
    public static <E> Resources setServletResourceLinks(Iterable<E> collection) {
        Resources<E> resources = new Resources<>(collection);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return resources;
    }
}
