package org.example.bucketsearch.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bucketsearch.dto.auth.AuthRequest;
import org.example.bucketsearch.dto.auth.AuthResponse;
import org.example.bucketsearch.dto.common.BaseResponse;
import org.example.bucketsearch.service.auth.AuthService;
import org.example.bucketsearch.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "회원 인증 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/kakao")
    public ResponseEntity<BaseResponse<AuthResponse>> kakaoLogin(@RequestBody AuthRequest authRequest) {
        AuthResponse response = authService.loginWithKakao(authRequest.accessToken());

        return ResponseEntity.ok(
                BaseResponse.success(response)
        );
    }

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
