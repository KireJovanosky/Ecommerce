package com.ecommerce.jwt.service;

import com.ecommerce.jwt.dao.CartDao;
import com.ecommerce.jwt.dao.ProductDao;
import com.ecommerce.jwt.dao.UserDao;
import com.ecommerce.jwt.entity.Cart;
import com.ecommerce.jwt.entity.Product;
import com.ecommerce.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    public Product addNewProduct(Product product){
        return productDao.save(product);
    }

    public List<Product> getAllProducts (int pageNumber, String searchKey){
        Pageable pageable = PageRequest.of(pageNumber, 8);
        if(searchKey.equals("")) {
            return (List<Product>) productDao.findAll(pageable);
        } else {
            return (List<Product>) productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
                    searchKey, searchKey, pageable
            );
        }
    }

    public void deleteProductDetails(Integer productId){
        productDao.deleteById(productId);
    }

    public Product getProductDetailsById(Integer productId){
        return productDao.findById(productId).get();
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId){
        if(isSingleProductCheckout && productId != 0){
        //buy single product
            List<Product> list = new ArrayList<>();
            Product product = productDao.findById(productId).get();
            list.add(product);
            return list;
        }else {
        // buy whole shopping cart
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userDao.findById(auth.getName()).get();
            List<Cart> cartItems = cartDao.findByUser(user);

            return cartItems.stream().map(i -> i.getProduct()).collect(Collectors.toList());
        }
    }

}
