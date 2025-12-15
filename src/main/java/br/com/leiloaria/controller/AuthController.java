package br.com.leiloaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.leiloaria.controller.dto.auth.LoginRequest;
import br.com.leiloaria.controller.dto.auth.LoginResponse;
import br.com.leiloaria.controller.dto.auth.RegisterRequest;
import br.com.leiloaria.controller.dto.auth.RegisterResponse;
import br.com.leiloaria.facade.Facade;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private Facade facade;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = facade.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = facade.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

}
