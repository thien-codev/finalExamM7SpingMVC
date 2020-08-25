/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thien.controller;

import com.thien.entity.OrderDetails;
import com.thien.entity.Orders;
import com.thien.entity.Products;
import com.thien.service.OrderDetailService;
import com.thien.service.OrderService;
import com.thien.service.ProductService;
import java.sql.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Admin
 */
@Controller
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/list")
    public String showListOfOrder(ModelMap model) {
        List<Orders> listOrders = orderService.getListOrders();
        model.addAttribute("listOrders", listOrders);
        return "home-page";
    }

    @RequestMapping(value = "/order-details")
    public String showOrderDetails(@RequestParam("orderId") int orderId, ModelMap model) {
        List<OrderDetails> listOrderDetails = orderDetailService.getListOrderDetailByOrderId(orderId);
        model.addAttribute("listOrderDetails", listOrderDetails);
        return "orderdetails-page";
    }

    @PostMapping(value = "/search-order")
    public String searchOrder(HttpServletRequest request, ModelMap model) {
        String url = "home-page";
        String dataSearch = request.getParameter("search");
        if (dataSearch.isEmpty()) {
            url = "redirect:/orders/list";
        } else {
            String check = String.valueOf(dataSearch.charAt(0));
            List<Orders> listOrders;
            try {
                Integer.parseInt(check);
                listOrders = orderService.getOrdersByOrderDate(Date.valueOf(dataSearch));
            } catch (Exception e) {
                listOrders = orderService.getOrdersByCustomerName(dataSearch);
                if (listOrders.isEmpty()) {
                    List<Products> listProducts = productService.getProductByName(dataSearch);
                    for (Products pro : listProducts) {
                        OrderDetails detail = orderDetailService.getOrderDetailsByProId(pro.getId());
                        Orders order = orderService.getOrderByID(detail.getOrder().getId());
                        listOrders.add(order);
                    }
                }
            }
            model.addAttribute("listOrders", listOrders);
        }
        return url;
    }
}
