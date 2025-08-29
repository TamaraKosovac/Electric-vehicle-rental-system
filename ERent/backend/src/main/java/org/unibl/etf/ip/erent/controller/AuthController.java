package org.unibl.etf.ip.erent.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.erent.dto.LoginDTO;
import org.unibl.etf.ip.erent.model.Client;
import org.unibl.etf.ip.erent.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginDTO login(@RequestBody LoginDTO request) {
        return authService.login(request);
    }
}
