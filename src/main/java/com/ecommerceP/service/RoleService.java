package com.ecommerceP.service;

import com.ecommerceP.Doa.Roledoa;
import com.ecommerceP.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private Roledoa roledoa;

    public Role createNewRole(Role role){
       return roledoa.save(role);
    }
}
