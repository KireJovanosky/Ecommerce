package com.ecommerce.jwtecommerce.dao;

import com.ecommerce.jwtecommerce.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, String> {
}
