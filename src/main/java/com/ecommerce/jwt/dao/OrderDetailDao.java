package com.ecommerce.jwt.dao;


import com.ecommerce.jwt.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {

}
