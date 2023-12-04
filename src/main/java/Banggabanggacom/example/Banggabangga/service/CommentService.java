package Banggabanggacom.example.Banggabangga.service;

import Banggabanggacom.example.Banggabangga.domain.Comment;
import Banggabanggacom.example.Banggabangga.domain.Post;
import Banggabanggacom.example.Banggabangga.domain.User;
import Banggabanggacom.example.Banggabangga.exception.CommentNotFoundException;
import Banggabanggacom.example.Banggabangga.exception.ErrorCode;
import Banggabanggacom.example.Banggabangga.exception.PostNotFoundException;
import Banggabanggacom.example.Banggabangga.repsitory.CommentRepository;
import Banggabanggacom.example.Banggabangga.repsitory.PostRepository;
import Banggabanggacom.example.Banggabangga.repsitory.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository,
                          PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public Long addComment(Long postId, String content, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_EXIST));

        String nickname = user.getNickname();

        Comment comment = Comment.builder()
                .user(user)
                .content(content)
                .nickname(nickname)
                .post(post)
                .build();

        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(ErrorCode.COMMENT_NOT_EXIST));
        comment.validateOwner(user.getId());
        commentRepository.delete(comment);
    }

}