package com.ContactManagement.Service.controller.token;

import com.ContactManagement.Service.helper.JwtUtil;
import com.ContactManagement.Service.dto.LoginRequestDto;
import com.ContactManagement.Service.dto.LoginResponseDto;
import com.ContactManagement.Service.service.token.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TokenController {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    public TokenController(JwtUtil jwtUtil, CustomUserDetailsServiceImpl customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<LoginResponseDto> generateToken(@Valid @RequestBody LoginRequestDto loginDto) {
        UserDetails userDetails= customUserDetailsService.loadUserByUsername(loginDto.getUsername());
        if (!userDetails.getPassword().equals(loginDto.getPassword()))
            throw new RuntimeException("Invalid user or password");
        LoginResponseDto loginResponseDto = LoginResponseDto.builder().token(jwtUtil.generateToken(userDetails)).build();
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }
}
