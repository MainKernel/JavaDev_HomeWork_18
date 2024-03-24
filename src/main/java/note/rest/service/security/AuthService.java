package note.rest.service.security;

import lombok.RequiredArgsConstructor;
import note.rest.model.entitiy.UserEntity;
import note.rest.model.dto.request.AuthRequest;
import note.rest.model.dto.request.RegistrationRequest;
import note.rest.model.dto.response.AuthResponse;
import note.rest.model.dto.response.RegistrationResponse;
import note.rest.repository.UserRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService service;
    private final AuthenticationManager manager;

    public RegistrationResponse register(RegistrationRequest request) {
        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .role(UserEntity.ServerRole.USER)
                .build();

        if (!isPasswordOk(request.getPassword())) {
            return RegistrationResponse.failed(RegistrationResponse.Error.invalidPassword);
        } else if (isUsernameOk(request.getUsername())) {
            return RegistrationResponse.failed(RegistrationResponse.Error.usernameAlreadyExists);
        } else if (isEmailOk(request.getEmail())) {
            return RegistrationResponse.failed(RegistrationResponse.Error.emailAlreadyExists);
        }

        try {
            UserEntity save = repository.save(user);
            return RegistrationResponse.success(save.getId()
                    , save.getUsername()
                    , save.getEmail()
                    , service.generateToken(user));

        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            e.printStackTrace();
        }
        return RegistrationResponse.failed(RegistrationResponse.Error.failToCrateUser);
    }

    public AuthResponse authenticate(AuthRequest request) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
            Optional<UserEntity> byUsername = repository.findByUsername(request.getUsername());
            if (byUsername.isPresent()) {
                return AuthResponse.success(service.generateToken(byUsername.get()));
            }
        } catch (DisabledException | LockedException | BadCredentialsException exception) {
            exception.printStackTrace();
        }
        return AuthResponse.failed(AuthResponse.Error.invalidCredentials);
    }

    private boolean isPasswordOk(String password) {
        Pattern pattern  = Pattern.compile("^(?=.*[A-Za-z0-9!@#$%^&*()_+]).{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();

    }

    private boolean isUsernameOk(String username) {
        return repository.findByUsername(username).isPresent();
    }

    private boolean isEmailOk(String email) {
       return repository.findByEmail(email).isPresent();
    }

}
