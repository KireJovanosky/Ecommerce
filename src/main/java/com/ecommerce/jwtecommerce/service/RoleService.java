package com.ecommerce.jwtecommerce.service;

import com.ecommerce.jwtecommerce.dao.RoleDao;
import com.ecommerce.jwtecommerce.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role createNewRole(Role role){
        return roleDao.save(role);
    }
}
