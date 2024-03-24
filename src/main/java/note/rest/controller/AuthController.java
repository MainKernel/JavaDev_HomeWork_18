package note.rest.controller;

import lombok.RequiredArgsConstructor;
import note.rest.model.dto.request.AuthRequest;
import note.rest.model.dto.request.RegistrationRequest;
import note.rest.model.dto.response.AuthResponse;
import note.rest.model.dto.response.RegistrationResponse;
import note.rest.service.security.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public RegistrationResponse register(@RequestBody RegistrationRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.authenticate(request);
    }

}
