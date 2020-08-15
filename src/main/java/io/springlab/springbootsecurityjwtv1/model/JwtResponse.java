package io.springlab.springbootsecurityjwtv1.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {


    private static final long serialVersionUID = -7355437345853243145L;

    private final String jwtToken;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
