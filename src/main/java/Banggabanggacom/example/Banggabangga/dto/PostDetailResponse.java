package Banggabanggacom.example.Banggabangga.dto;

import Banggabanggacom.example.Banggabangga.domain.Comment;
import Banggabanggacom.example.Banggabangga.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostDetailResponse {

    private Long id;
    private String nickname;
    private String title;
    private String content;
    private List<Comment> comments;
    private int likeCount;
    private LocalDateTime createdAt;
    private boolean isLiked;

    public PostDetailResponse() {
    }

    @Builder
    private PostDetailResponse(Long id, String nickname, String title, String content,
                               LocalDateTime createdAt, int likeCount, List<Comment> comments, boolean isLiked) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.comments = comments;
        this.isLiked = isLiked;
    }


    public static PostDetailResponse of(Post post, boolean isLiked) {
        return PostDetailResponse.builder()
                .id(post.getId())
                .nickname(post.getWriterNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .likeCount(post.getLikeCount())
                .comments(post.getComments())
                .isLiked(isLiked)
                .build();
    }
}