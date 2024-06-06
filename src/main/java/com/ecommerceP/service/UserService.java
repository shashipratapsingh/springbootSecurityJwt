package com.ecommerceP.service;

import com.ecommerceP.Doa.Roledoa;
import com.ecommerceP.Doa.UserDao;
import com.ecommerceP.entity.Role;
import com.ecommerceP.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private Roledoa roledoa;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user)
    {
        Role role=roledoa.findById("User").get();
        Set<Role> roles=new HashSet<>();
        roles.add(role);
        user.setRole(roles);
        user.setUserpassword(getEncodedPassword(user.getUserpassword()));
        return userDao.save(user);
    }

    public void initRolesAndUser(){
        Role adminRole =new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDesription("Admin Role");
        roledoa.save(adminRole);

        Role userRole =new Role();
        userRole.setRoleName("User");
        userRole.setRoleDesription("User Role");
        roledoa.save(userRole);

        User adminUser =new User();
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        adminUser.setUserName("admin123");
        adminUser.setUserpassword(getEncodedPassword("admin@password"));
        Set<Role> adminRoles=new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

        User user =new User();
        user.setUserFirstName("shashi");
        user.setUserLastName("pratap");
        user.setUserName("shashi123");
        user.setUserpassword(getEncodedPassword("shashi@password"));
        Set<Role> userRoles=new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        userDao.save(user);
    }
    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }
}
