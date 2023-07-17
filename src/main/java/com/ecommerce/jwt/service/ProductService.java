package com.ecommerce.jwt.service;

import com.ecommerce.jwt.dao.ProductDao;
import com.ecommerce.jwt.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public Product addNewProduct(Product product){
        return productDao.save(product);
    }

    public List<Product> getAllProducts (){
        return (List<Product>) productDao.findAll();
    }

    public void deleteProductDetails(Integer productId){
        productDao.deleteById(productId);
    }

    public Product getProductDetailsById(Integer productId){
        return productDao.findById(productId).get();
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId){
        if(isSingleProductCheckout){
        //buy single product
            List<Product> list = new ArrayList<>();
            Product product = productDao.findById(productId).get();
            list.add(product);
            return list;
        }else {
        // buy whole shopping cart

        }
        return new ArrayList<>();
    }

}
