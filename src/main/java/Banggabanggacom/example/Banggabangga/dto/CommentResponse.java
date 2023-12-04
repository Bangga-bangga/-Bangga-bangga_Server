package Banggabanggacom.example.Banggabangga.dto;

import Banggabanggacom.example.Banggabangga.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private final Long id;
    private final String nickname;
    private final String content;
    private final LocalDateTime createdAt;

    public CommentResponse(Long id, String nickname, String content, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(comment.getId(), comment.getNickname(), comment.getContent(),
                comment.getCreatedAt());
    }
}
