package com.ecommerce.jwt.service;

import com.ecommerce.jwt.configuration.JwtRequestFilter;
import com.ecommerce.jwt.dao.CartDao;
import com.ecommerce.jwt.dao.OrderDetailDao;
import com.ecommerce.jwt.dao.ProductDao;
import com.ecommerce.jwt.dao.UserDao;
import com.ecommerce.jwt.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {

    private static final String ORDER_PLACED = "Placed";

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout){
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for (OrderProductQuantity o: productQuantityList){
            Product product = productDao.findById(o.getProductId()).get();

            String currentUser = JwtRequestFilter.CURRENT_USER;
            User user = userDao.findById(currentUser).get();

            OrderDetail orderDetail = new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductDiscountedPrice() * o.getQuantity(),
                    product,
                    user
            );
            // empty the cart
            if (!isSingleProductCheckout) {
               List<Cart> items = cartDao.findByUser(user);
               items.stream().forEach(i -> cartDao.deleteById(i.getCartId()));
            }

            orderDetailDao.save(orderDetail);
        }
    }

    public List<OrderDetail> getOrderDetails(){
        String currentUser = JwtRequestFilter.CURRENT_USER;

        User user = userDao.findById(currentUser).get();

        return orderDetailDao.findByUser(user);
    }

    public List<OrderDetail> getAllOrderDetails(String status) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        if (status.equals("All")) {
            orderDetailDao.findAll().forEach(
                    o -> orderDetails.add(o)
            );

        }else {
            orderDetailDao.findByOrderStatus(status).forEach(
                    o -> orderDetails.add(o)
            );
        }
            return orderDetails;
    }

    public void markOrderAsDelivered(Integer orderId){
        OrderDetail orderDetail = orderDetailDao.findById(orderId).get();
        if (orderDetail != null) {
            orderDetail.setOrderStatus("Delivered");
            orderDetailDao.save(orderDetail);
        }
    }
}
