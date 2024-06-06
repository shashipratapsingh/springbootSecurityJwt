package com.ecommerceP.service;

import com.ecommerceP.Doa.UserDao;
import com.ecommerceP.entity.JwtRequest;
import com.ecommerceP.entity.JwtResponse;
import com.ecommerceP.entity.User;
import com.ecommerceP.util.JWtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JWtUtil jWtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;



    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userName=jwtRequest.getUserName();
        String userPassword=jwtRequest.getUserPassword();
        authenticate(userName,userPassword);
        final UserDetails userDetails= loadUserByUsername(userName);
        String newGenerateTokrn=jWtUtil.generateToken(userDetails);
        User user=userDao.findById(userName).get();
        return new JwtResponse(user,newGenerateTokrn);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userDao.findById(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new org.springframework.security.core.userdetails.User(user.getUserName(),
                    user.getUserpassword(),
                    getAuthorities(user));
        } else {
            throw new UsernameNotFoundException("Username is not valid");
        }
    }


    private Set getAuthorities(User user){
        Set authorities=new HashSet();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });

        return authorities;
    }


    private void authenticate(String userName,String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        }catch (DisabledException e) {
            throw new Exception("user is desabled");
        }catch (BadCredentialsException e){
            throw new Exception("Bad credentials from user");
        }
    }
}
