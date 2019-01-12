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

package org.sergei.rest.controller;

import io.swagger.annotations.*;
import org.sergei.rest.dto.OrderDTO;
import org.sergei.rest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/api/v1/customers/{customerId}/orders",
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation("Get all order by customer number")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @GetMapping("/v1/customers/{customerId}/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@ApiParam(value = "Customer ID whose orders should be found", required = true)
                                                                @PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(orderService.findAll(customerId), HttpStatus.OK);
    }

    @ApiOperation("Get order by customer and order numbers")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer or order ID")
            }
    )
    @GetMapping("/v1/customers/{customerId}/orders/{orderId}")
    public ResponseEntity<OrderDTO> getOrderByCustomerAndOrderId(@ApiParam(value = "Customer ID whose order should be found", required = true)
                                                                 @PathVariable("customerId") Long customerId,
                                                                 @ApiParam(value = "Order ID which should be found", required = true)
                                                                 @PathVariable("orderId") Long orderId) {
        return new ResponseEntity<>(orderService.findOne(customerId, orderId), HttpStatus.OK);
    }

    @ApiOperation("Get all orders with specific product code")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid product code")
            }
    )
    @GetMapping("/v1/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByProductCode(@ApiParam(value = "Code of the product which should be found", required = true)
                                                                 @RequestParam("prod-code") String productCode) {
        return new ResponseEntity<>(orderService.findAllByProductCode(productCode), HttpStatus.OK);
    }

    @ApiOperation("Add a new order for the customer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer ID")
            }
    )
    @PostMapping(value = {
            "/v1/customers/{customerId}/orders",
            "/v2/customers/{customerId}/orders"
    }, consumes = "application/json")
    public ResponseEntity<OrderDTO> createOrder(@ApiParam(value = "Customer ID who created order", required = true)
                                                @PathVariable("customerId") Long customerId,
                                                @ApiParam(value = "Saved order", required = true)
                                                @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.save(customerId, orderDTO), HttpStatus.CREATED);
    }

    @ApiOperation("Update order by customer and order numbers")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer or order ID")
            }
    )
    @PutMapping(value = {
            "/v1/customers/{customerId}/orders/{orderId}",
            "/v2/customers/{customerId}/orders/{orderId}"
    }, consumes = "application/json")
    public ResponseEntity<OrderDTO> updateRecord(@ApiParam(value = "Customer ID whose order should be updated", required = true)
                                                 @PathVariable("customerId") Long customerId,
                                                 @ApiParam(value = "Order ID which should be updated", required = true)
                                                 @PathVariable("orderId") Long orderId,
                                                 @ApiParam(value = "Updated order", required = true)
                                                 @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.update(customerId, orderId, orderDTO), HttpStatus.OK);
    }

    @ApiOperation("Delete order by customer and order numbers")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Invalid customer or order ID")
            }
    )
    @DeleteMapping({
            "/v1/customers/{customerId}/orders/{orderId}",
            "/v2/customers/{customerId}/orders/{orderId}"
    })
    public ResponseEntity<OrderDTO> deleteOrderByCustomerIdAndOrderId(@ApiParam(value = "Customer ID whose order should be deleted", required = true)
                                                                      @PathVariable("customerId") Long customerId,
                                                                      @ApiParam(value = "Order ID whose order should be deleted", required = true)
                                                                      @PathVariable("orderId") Long orderId) {
        orderService.deleteByCustomerIdAndOrderId(customerId, orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
