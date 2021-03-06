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

package org.sergei.rest.controller.v2;

import io.swagger.annotations.*;
import org.sergei.rest.dto.v2.OrderDTOV2;
import org.sergei.rest.service.v2.OrderServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.sergei.rest.controller.hateoas.LinkUtil.*;

/**
 * V2 of order controller
 *
 * @author Sergei Visotsky
 */
@Api(
        value = "/api/v2/customers/{customerId}/orders",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class OrderControllerV2 {

    private final OrderServiceV2 orderServiceV2;

    @Autowired
    public OrderControllerV2(OrderServiceV2 orderServiceV2) {
        this.orderServiceV2 = orderServiceV2;
    }

    @ApiOperation("Get all order by customer ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping("/v2/customers/{customerId}/orders")
    public ResponseEntity getOrdersByCustomerIdV2(@ApiParam(value = "Customer ID whose orders should be found", required = true)
                                                  @PathVariable("customerId") Long customerId) {
        List<OrderDTOV2> orderDTOV2List = orderServiceV2.findAllByCustomerIdV2(customerId);
        return new ResponseEntity<>(setLinksForAllOrders(orderDTOV2List), HttpStatus.OK);
    }

    @ApiOperation("Get all order by customer ID paginated")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping(value = "/v2/customers/{customerId}/orders", params = {"page", "size"})
    public ResponseEntity getOrdersByCustomerIdPaginatedV2(@ApiParam(value = "Customer ID whose orders should be found", required = true)
                                                           @PathVariable("customerId") Long customerId,
                                                           @ApiParam("Number of page")
                                                           @RequestParam("page") int page,
                                                           @ApiParam("Number of elements per page")
                                                           @RequestParam("size") int size) {
        Page<OrderDTOV2> orderDTOV2List = orderServiceV2.findAllByCustomerIdPaginatedV2(customerId, page, size);
        return new ResponseEntity<>(setLinksForAllOrders(orderDTOV2List), HttpStatus.OK);
    }

    @ApiOperation("Get order by customer and order numbers")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer or order ID")
            }
    )
    @GetMapping("/v2/customers/{customerId}/orders/{orderId}")
    public ResponseEntity<OrderDTOV2> getOrderByCustomerAndOrderIdV2(@ApiParam(value = "Customer ID whose order should be found", required = true)
                                                                     @PathVariable("customerId") Long customerId,
                                                                     @ApiParam(value = "Order ID which should be found", required = true)
                                                                     @PathVariable("orderId") Long orderId) {
        OrderDTOV2 orderDTOV2 = orderServiceV2.findOneV2(customerId, orderId);
        return new ResponseEntity<>(setLinksForOneOrder(orderDTOV2), HttpStatus.OK);
    }

    @ApiOperation("Get all orders with specific product code")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid product code")
            }
    )
    @GetMapping("/v2/orders")
    public ResponseEntity getOrdersByProductCodeV2(@ApiParam(value = "Code of the product which should be found", required = true)
                                                   @RequestParam("prod-code") String productCode) {
        List<OrderDTOV2> orderDTOV2List = orderServiceV2.findAllByProductCodeV2(productCode);
        return new ResponseEntity<>(setServletResourceLinks(orderDTOV2List), HttpStatus.OK);
    }
}
