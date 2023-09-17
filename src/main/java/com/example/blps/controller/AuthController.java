package com.example.blps.controller;

import com.example.blps.exceptions.RefreshTokenException;
import com.example.blps.exceptions.ResourceAlreadyExistsException;
import com.example.blps.module.RefreshToken;
import com.example.blps.module.User;
import com.example.blps.module.request.LogInRequestDTO;
import com.example.blps.module.request.LogOutRequestDTO;
import com.example.blps.module.request.RefreshTokenRequestDTO;
import com.example.blps.module.request.SignUpRequestDTO;
import com.example.blps.module.response.JwtResponseDTO;
import com.example.blps.module.response.MessageResponseDTO;
import com.example.blps.module.response.RefreshTokenResponseDTO;
import com.example.blps.repo.UserRepository;
import com.example.blps.security.jwt.JwtUtils;
import com.example.blps.security.module.UserDetailsImpl;
import com.example.blps.security.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "${cors.urls}")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LogInRequestDTO loginRequestDto) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponseDTO(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername()));

    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenRequestDTO request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getEmail());
                    return ResponseEntity.ok(new RefreshTokenResponseDTO(token, requestRefreshToken));
                })
                .orElseThrow(() -> new RefreshTokenException(requestRefreshToken,
                        "Refresh Token нет в базе"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestDTO signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail()))
            throw new ResourceAlreadyExistsException("Данный email уже занят");

        User user = new User(signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponseDTO("Пользователь зарегистрирован успешно"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequestDTO logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
        return ResponseEntity.ok(new MessageResponseDTO("Выход из аккаунта осуществлен успешно"));
    }
}