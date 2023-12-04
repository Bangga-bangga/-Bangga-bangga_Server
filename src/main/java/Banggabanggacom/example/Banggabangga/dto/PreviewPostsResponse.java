package Banggabanggacom.example.Banggabangga.dto;

import Banggabanggacom.example.Banggabangga.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PreviewPostsResponse {
    private Long id;
    private String writer;
    private String title;
    private LocalDateTime createdAt;
    private int likeCount;
    private int commentCount;

    @Builder
    private PreviewPostsResponse(Long id, String title, String writer, LocalDateTime createdAt,
                                 int likeCount, int commentCount) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public static PreviewPostsResponse from(Post post) {
        return PreviewPostsResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .writer(post.getWriterNickname())
                .createdAt(post.getCreatedAt())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .build();
    }
}
