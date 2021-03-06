/*
 * Copyright 2018-2019 the original author.
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

package org.sergei.rest.controller.hateoas;

import org.sergei.rest.controller.PhotoController;
import org.sergei.rest.controller.v2.CustomerControllerV2;
import org.sergei.rest.controller.v2.OrderControllerV2;
import org.sergei.rest.controller.v2.ProductControllerV2;
import org.sergei.rest.dto.v2.CustomerDTOV2;
import org.sergei.rest.dto.v2.OrderDTOV2;
import org.sergei.rest.dto.v2.ProductDTOV2;
import org.sergei.rest.model.CustomerReport;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author Sergei Visotsky
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
                    ControllerLinkBuilder.methodOn(OrderControllerV2.class)
                            .getOrdersByCustomerIdV2(customer.getCustomerId())).withRel("orders");
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
        Link link = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(CustomerControllerV2.class)
                        .getCustomerByIdV2(customerId)).withSelfRel();
        Link ordersLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(OrderControllerV2.class)
                        .getOrdersByCustomerIdV2(customerId)).withRel("orders");
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

    /**
     * Set links for the products
     *
     * @param products collection of product entity
     * @return collection with links set
     */
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
     * Method to set links for the customer report
     *
     * @param customerId      customer ID to be set in links
     * @param customerReports Collection of elements for customer report
     * @return collection with links set
     */
    public static Resources setLinksForReport(Long customerId, Iterable<CustomerReport> customerReports) {
        Resources reports = setServletResourceLinks(customerReports);
        Link customer = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(CustomerControllerV2.class)
                        .getCustomerByIdV2(customerId)).withRel("customer");
        Link allCustomers = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(CustomerControllerV2.class)
                        .getAllCustomersV2()).withRel("allCustomers");
        reports.add(customer);
        reports.add(allCustomers);
        return reports;
    }

    /**
     * Set links for the product JSON response
     *
     * @param productDTOV2 product entity to set links
     * @return entity with links set
     */
    public static ProductDTOV2 setLinksForProduct(ProductDTOV2 productDTOV2) {
        Link link = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(ProductControllerV2.class)
                        .getProductByCodeV2(productDTOV2.getProductCode())).withSelfRel();
        productDTOV2.add(link);
        return productDTOV2;
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
