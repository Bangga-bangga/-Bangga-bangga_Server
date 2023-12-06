package Banggabanggacom.example.Banggabangga.service;

import Banggabanggacom.example.Banggabangga.domain.Like;
import Banggabanggacom.example.Banggabangga.domain.Post;
import Banggabanggacom.example.Banggabangga.domain.User;
import Banggabanggacom.example.Banggabangga.dto.LikeFlipResponse;
import Banggabanggacom.example.Banggabangga.exception.ErrorCode;
import Banggabanggacom.example.Banggabangga.exception.PostNotFoundException;
import Banggabanggacom.example.Banggabangga.exception.User.UserNotFoundException;
import Banggabanggacom.example.Banggabangga.repsitory.CommentRepository;
import Banggabanggacom.example.Banggabangga.repsitory.LikeRepository;
import Banggabanggacom.example.Banggabangga.repsitory.PostRepository;
import Banggabanggacom.example.Banggabangga.repsitory.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository,
                       PostRepository postRepository, CommentRepository commentRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public LikeFlipResponse flipPostLike(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_EXIST));

        int likeCount = flipPostLike(user.getId(), post);
        boolean liked = likeRepository.existsByPostAndUserId(post, user.getId());

        return new LikeFlipResponse(likeCount, liked);
    }

    private int flipPostLike(Long userId, Post post) {
        final Optional<Like> like = likeRepository.findByPostAndUserId(post, userId);
        if (like.isPresent()) {
            post.deleteLike(like.get());
            postRepository.decreaseLikeCount(post.getId());
            return post.getLikeCount() - 1;
        }

        addNewLike(userId, post);
        postRepository.increaseLikeCount(post.getId());
        return post.getLikeCount() + 1;
    }

    private void addNewLike(Long userId, Post post) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_EXIST));
        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();
        likeRepository.save(like);
    }

}