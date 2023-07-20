package com.ecommerce.jwt.service;


import com.ecommerce.jwt.configuration.JwtRequestFilter;
import com.ecommerce.jwt.dao.CartDao;
import com.ecommerce.jwt.dao.ProductDao;
import com.ecommerce.jwt.dao.UserDao;
import com.ecommerce.jwt.entity.Cart;
import com.ecommerce.jwt.entity.Product;
import com.ecommerce.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;


    public Cart addToCart(Integer productId) {
        Product product = productDao.findById(productId).get();

        String username = JwtRequestFilter.CURRENT_USER;

        User user = null;

        if (username != null){
            user = userDao.findById(username).get();
        }

        if (product != null && user != null){
            Cart cart = new Cart(product, user);
            return cartDao.save(cart);
        }
        return null;
    }

}
