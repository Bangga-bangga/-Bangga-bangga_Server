package Banggabanggacom.example.Banggabangga.controller;

import Banggabanggacom.example.Banggabangga.dto.UserSignupRequest;
import Banggabanggacom.example.Banggabangga.service.SignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-up")
public class UserSignupController {

    private final SignupService signupService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    /*
    가능하다면 이매일 인증 추가
    */

    @PostMapping
    public Object register(@RequestBody @Valid UserSignupRequest request) throws IOException {
        try {
            request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            signupService.signup(request);
            return Map.of("result", "성공");
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(value = "/exists", produces = "application/json; charset=UTF-8")
    public Object checkEmail(@RequestParam String nickname) throws IOException {
        try {
            signupService.checkDuplicateNickName(nickname);
            return Map.of("result", "중복되지 않은 이메일입니다.");
        } catch (Exception e) {
            throw e;
        }

    }

}

