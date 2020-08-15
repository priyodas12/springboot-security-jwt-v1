package io.springlab.springbootsecurityjwtv1.controller;

import io.springlab.springbootsecurityjwtv1.service.JwtUserDetailsService;
import io.springlab.springbootsecurityjwtv1.utility.JWTTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class GenericController {

    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    //to validate jwt token instead of using filter now hardcoded.
    @Value("spring.application.username")
    String username;

    @RequestMapping(value = "/ping" ,method = RequestMethod.GET)
    public ResponseEntity<String> returnSignedMessage(@RequestHeader("Authorization") String auth ){

        final Logger logger= LoggerFactory.getLogger(GenericController.class);

        String token=auth.substring(7);

        final UserDetails userDetails=jwtUserDetailsService.loadUserByUsername(username);

        logger.info("got user info from application.yml {}",username);

        if(jwtTokenUtil.validateToken(token,userDetails)){
            logger.info("validation done in utility class {}",jwtTokenUtil.validateToken(token,userDetails));
            return ResponseEntity.ok("Hey There\t"+new Date());
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
