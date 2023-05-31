package com.example.managestore.controller;

import com.example.managestore.configuration.jwt.JwtService;
import com.example.managestore.configuration.jwt.ResponseJwt;
import com.example.managestore.service.manageEmployee.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticateController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtService jwtService;
    private final UserService userService;
    @GetMapping("/auth")
    public ResponseEntity<ResponseJwt> login(@RequestParam(name = "username") String username,
                                             @RequestParam(name = "password") String password){
        userService.authenticationUser(username,password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(authentication);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseJwt("BearToken",jwt));
    }
}
