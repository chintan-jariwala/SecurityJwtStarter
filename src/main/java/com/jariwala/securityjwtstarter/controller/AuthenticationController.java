package com.jariwala.securityjwtstarter.controller;

import com.jariwala.securityjwtstarter.config.JwtTokenProvider;
import com.jariwala.securityjwtstarter.model.RegisterForm;
import com.jariwala.securityjwtstarter.model.SignInForm;
import com.jariwala.securityjwtstarter.model.User;
import com.jariwala.securityjwtstarter.repository.UserRepository;
import com.jariwala.securityjwtstarter.utils.ApiResponse;
import com.jariwala.securityjwtstarter.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Controller
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> signIn(@Valid @RequestBody SignInForm form) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        form.getUsername(),
                        form.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return ApiResponse.successToken(Constants.LOGIN_SUCCESS, jwt);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterForm form) {
        if (userRepository.existsByUsername(form.getUsername())) {
            return ApiResponse.validationError("Username is taken");
        }
        if (userRepository.existsByEmail(form.getEmail())) {
            return ApiResponse.validationError("Email is taken");
        }

        User user = new User(form.getUsername(),
                passwordEncoder.encode(form.getPassword()),
                form.getName(),
                form.getEmail());
        User saved = userRepository.save(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/users/{username}")
                .buildAndExpand(saved.getUsername()).toUri();

        return ResponseEntity.created(uri).body(new ApiResponse(HttpStatus.CREATED.name(),
                "User has been created"));
    }
}
