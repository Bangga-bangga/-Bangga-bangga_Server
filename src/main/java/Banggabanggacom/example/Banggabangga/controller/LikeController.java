package Banggabanggacom.example.Banggabangga.controller;

import Banggabanggacom.example.Banggabangga.config.auth.PrincipalDetails;
import Banggabanggacom.example.Banggabangga.domain.User;
import Banggabanggacom.example.Banggabangga.dto.LikeFlipResponse;
import Banggabanggacom.example.Banggabangga.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PutMapping("/posts/{id}/like")
    public ResponseEntity<LikeFlipResponse> flipPostLike(@PathVariable("id") Long postId,
                                                         Authentication authentication) {
        LikeFlipResponse likeFlipResponse = likeService.flipPostLike(postId, getUser(authentication));
        return ResponseEntity.ok(likeFlipResponse);
    }

    public User getUser(Authentication authentication) {
        return getPrincipalUser(authentication);
    }

    private User getPrincipalUser(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return principal.getUser();
    }
}