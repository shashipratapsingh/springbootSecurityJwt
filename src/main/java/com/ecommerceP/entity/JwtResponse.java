package com.ecommerceP.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JwtResponse {
    private User user;
    private String jwtToken;


    public JwtResponse(User user ,String jwtToken) {
        this.user=user;
        this.jwtToken=jwtToken;
    }
}
