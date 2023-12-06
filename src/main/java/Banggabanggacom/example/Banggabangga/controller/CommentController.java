package Banggabanggacom.example.Banggabangga.controller;

import Banggabanggacom.example.Banggabangga.config.auth.PrincipalDetails;
import Banggabanggacom.example.Banggabangga.domain.User;
import Banggabanggacom.example.Banggabangga.service.CommentService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<Void> addComment(@PathVariable(name = "id") Long postId,
                                           @NotBlank @RequestBody String content,
                                           Authentication authentication) {
        Long commentId = commentService.addComment(postId, content, getUser(authentication));
        return ResponseEntity.created(URI.create("/comments/" + commentId)).build();
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "id") Long commentId,
                                              Authentication authentication) {
        commentService.deleteComment(commentId, getUser(authentication));
        return ResponseEntity.noContent().build();
    }

    public User getUser(Authentication authentication) {
        return getPrincipalUser(authentication);
    }

    private User getPrincipalUser(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return principal.getUser();
    }
}