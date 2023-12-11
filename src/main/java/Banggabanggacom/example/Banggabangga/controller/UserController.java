package Banggabanggacom.example.Banggabangga.controller;

import Banggabanggacom.example.Banggabangga.config.auth.PrincipalDetails;
import Banggabanggacom.example.Banggabangga.domain.User;
import Banggabanggacom.example.Banggabangga.dto.MyPageResponse;
import Banggabanggacom.example.Banggabangga.exception.ErrorCode;
import Banggabanggacom.example.Banggabangga.exception.User.UserNotFoundException;
import Banggabanggacom.example.Banggabangga.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    //user 정보
    @GetMapping("/user/{email}")
    public User retrieveUser(@PathVariable String email) {
        User user = userService.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_EXIST);
        }
        return user;
    }

    @GetMapping(path = "/user/me")
    public ResponseEntity<MyPageResponse> findMyPosts(
            @PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable, Authentication authentication) {
        MyPageResponse myPageResponse = userService.findUserInfo(pageable, getUser(authentication));
        return ResponseEntity.ok(myPageResponse);
    }


    //비밀번호 변경
    @GetMapping("/user/update")
    public Object updateUser(@RequestParam String password, Authentication authentication) {
        User user = getPrincipalUser(authentication);
        userService.update(user, password);
        return Map.of("result", "비밀번호 변경 성공");
    }

    //회원탈퇴
    @DeleteMapping("/user/delete")
    public Object deleteUser(Authentication authentication) {
        userService.delete(getPrincipalUser(authentication).getEmail());
        return Map.of("result", "회원 탈퇴 성공성공");
    }

    @GetMapping
    public User getUser(Authentication authentication) {
        return getPrincipalUser(authentication);
    }

    private User getPrincipalUser(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return principal.getUser();
    }
}
