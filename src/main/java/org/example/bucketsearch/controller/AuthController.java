package org.example.bucketsearch.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bucketsearch.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "회원 인증 API")
public class AuthController {

    private final UserService userService;

//    @PostMapping
//    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
//        return userService.findUserByEmailAndPassword(request.email(), request.password())
//                .map(this::toResponse)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.status(401).build());
//    }
//
//    private AuthResponse toResponse(User user) {
//        return new AuthResponse(
//                user.getId(),
//                user.getEmail(),
//                user.getUsername(),
//                user.getProfileImgUrl()
//        );
//    }
}
