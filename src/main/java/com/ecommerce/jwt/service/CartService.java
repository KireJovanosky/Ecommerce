package com.ecommerce.jwt.service;


import com.ecommerce.jwt.dao.CartDao;
import com.ecommerce.jwt.dao.ProductDao;
import com.ecommerce.jwt.dao.UserDao;
import com.ecommerce.jwt.entity.Cart;
import com.ecommerce.jwt.entity.Product;
import com.ecommerce.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = null;

        if (auth.getName() != null){
            user = userDao.findById(auth.getName()).get();
        }

        List<Cart> cartList = cartDao.findByUser(user);

        List<Cart> filteredList = cartList.stream().filter(i -> i.getProduct().getProductId() == productId).collect(Collectors.toList());

        if(filteredList.size() > 0) {
            return  null;
        }


        if (product != null && user != null){
            Cart cart = new Cart(product, user);
            return cartDao.save(cart);
        }
        return null;
    }

    public List<Cart> getCartDetails(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findById(auth.getName()).get();
        return cartDao.findByUser(user);
    }

    public void deleteCartItem(Integer cartId) {
        cartDao.deleteById(cartId);
    }

}
