package Banggabanggacom.example.Banggabangga.service;

import Banggabanggacom.example.Banggabangga.domain.Category;
import Banggabanggacom.example.Banggabangga.domain.Post;
import Banggabanggacom.example.Banggabangga.domain.User;
import Banggabanggacom.example.Banggabangga.dto.NewPostRequest;
import Banggabanggacom.example.Banggabangga.dto.PostDetailResponse;
import Banggabanggacom.example.Banggabangga.dto.PostUpdateRequest;
import Banggabanggacom.example.Banggabangga.dto.PostsResponse;
import Banggabanggacom.example.Banggabangga.exception.ErrorCode;
import Banggabanggacom.example.Banggabangga.exception.PostNotFoundException;
import Banggabanggacom.example.Banggabangga.exception.User.AuthorizationException;
import Banggabanggacom.example.Banggabangga.repsitory.CommentRepository;
import Banggabanggacom.example.Banggabangga.repsitory.LikeRepository;
import Banggabanggacom.example.Banggabangga.repsitory.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public PostService(PostRepository postRepository,
                       CommentRepository commentRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
    }

    @Transactional
    public Long addPost(Category category, NewPostRequest newPostRequest, User user) {
        String writerNickname = user.getNickname();
        Post post = createPost(newPostRequest, writerNickname, user, category);
        Post savedPost = postRepository.save(post);

        return savedPost.getId();
    }

    private Post createPost(NewPostRequest newPostRequest, String writerNickname, User user, Category category) {
        return Post.builder()
                .title(newPostRequest.getTitle())
                .content(newPostRequest.getContent())
                .writerNickname(writerNickname)
                .user(user)
                .category(category)
                .build();
    }

    @Transactional
    public PostDetailResponse findPost(Long postId, User user) {
        Post foundPost = findPostObject(postId);
        boolean liked = likeRepository.existsByPostAndUserId(foundPost, user.getId());

        return PostDetailResponse.of(foundPost,liked);
    }

    private Post findPostObject(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_EXIST));
    }

    public PostsResponse findPostsByCategory(Category category, Pageable pageable) {
        Page<Post> posts = postRepository.findPostsByCategory(category, pageable);
        return PostsResponse.of(posts.getContent(),posts.getTotalPages());
    }

    public PostsResponse findMyPosts(Pageable pageable, User user) {
        Page<Post> posts = postRepository.findPostsByUserOrderByCreatedAtDesc(pageable, user);
        return PostsResponse.of(posts.getContent(), posts.getTotalPages());
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateRequest postUpdateRequest, User user) {
        Post post = findPostObject(postId);

        validateOwner(user, post);
        post.updateTitle(postUpdateRequest.getTitle());
        post.updateContent(postUpdateRequest.getContent());
    }

    @Transactional
    public void deletePost(Long id, User user) {
        Post post = findPostObject(id);
        validateOwner(user, post);

        commentRepository.deleteAllByPost(post);
        likeRepository.deleteAllByPost(post);
        likeRepository.deleteAllByPost(post);
        postRepository.delete(post);
    }

    private void validateOwner(User user, Post post) {
        if (!post.isOwner(user.getId())) {
            throw new AuthorizationException(ErrorCode.HAS_NOT_AUTHORIZATION);
        }
    }
}