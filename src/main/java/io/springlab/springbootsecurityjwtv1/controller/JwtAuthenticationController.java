package io.springlab.springbootsecurityjwtv1.controller;


import io.springlab.springbootsecurityjwtv1.model.JwtRequest;
import io.springlab.springbootsecurityjwtv1.model.JwtResponse;
import io.springlab.springbootsecurityjwtv1.service.JwtUserDetailsService;
import io.springlab.springbootsecurityjwtv1.utility.JWTTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    final static Logger logger= LoggerFactory.getLogger(JwtAuthenticationController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @RequestMapping(value = {"/authenticate"},method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        authenticateRequestInfo(jwtRequest.getUsername(),jwtRequest.getPassword());

        final UserDetails userDetails=jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final String token=jwtTokenUtil.generateJWTToken(userDetails);
        logger.info("createAuthenticationToken generated {}",token);
        return ResponseEntity.ok(new JwtResponse(token));

    }

    private void authenticateRequestInfo(String username,String password) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            logger.info("authenticateRequestInfo got {}",username);
        }catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
