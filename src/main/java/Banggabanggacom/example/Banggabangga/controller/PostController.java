package Banggabanggacom.example.Banggabangga.controller;

import Banggabanggacom.example.Banggabangga.config.auth.PrincipalDetails;
import Banggabanggacom.example.Banggabangga.domain.Category;
import Banggabanggacom.example.Banggabangga.domain.User;
import Banggabanggacom.example.Banggabangga.dto.NewPostRequest;
import Banggabanggacom.example.Banggabangga.dto.PostDetailResponse;
import Banggabanggacom.example.Banggabangga.dto.PostUpdateRequest;
import Banggabanggacom.example.Banggabangga.dto.PostsResponse;
import Banggabanggacom.example.Banggabangga.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //Location Header에 해당 게시물 상세보기 페이지 링크 줌
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDetailResponse> findPost(@PathVariable Long id,
                                                       Authentication authentication) {
        PostDetailResponse postResponse = postService.findPost(id, getUser(authentication));
        return ResponseEntity.ok().body(postResponse);
    }

    @PostMapping("/{category}/posts")
    public ResponseEntity<Long> addPost(@PathVariable("category") String categoryString,
                                        @Valid @RequestBody NewPostRequest newPostRequest,
                                        Authentication authentication) {
        Category category = Category.valueOf(categoryString.toUpperCase());
        Long postId = postService.addPost(category, newPostRequest, getUser(authentication));
        return ResponseEntity.created(URI.create("/posts/" + postId)).body(postId);
    }

    @GetMapping("/{category}/posts")
    public ResponseEntity<PostsResponse> findPosts(
            @PathVariable("category") String categoryString,
            @PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable) {
        Category category = Category.valueOf(categoryString.toUpperCase());
        PostsResponse postsResponse = postService.findPostsByCategory(category, pageable);
        return ResponseEntity.ok(postsResponse);
    }

    @GetMapping(path = "/posts/me")
    public ResponseEntity<PostsResponse> findMyPosts(
            @PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable, Authentication authentication) {
        PostsResponse myPosts = postService.findMyPosts(pageable, getUser(authentication));
        return ResponseEntity.ok(myPosts);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id,
                                           @Valid @RequestBody PostUpdateRequest postUpdateRequest,
                                           Authentication authentication) {
        postService.updatePost(id, postUpdateRequest, getUser(authentication));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, Authentication authentication) {
        postService.deletePost(id, getUser(authentication));
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