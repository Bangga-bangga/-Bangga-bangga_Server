package Banggabanggacom.example.Banggabangga.domain;

import Banggabanggacom.example.Banggabangga.exception.ErrorCode;
import Banggabanggacom.example.Banggabangga.exception.User.AuthorizationException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String nickname;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    protected Comment() {
    }

    @Builder
    public Comment(User user, Post post, String nickname, String content ) {
        this.user = user;
        this.post = post;
        this.nickname = nickname;
        this.content = content;
    }

    public void validateOwner(Long accessMemberId) {
        if (!accessMemberId.equals(user.getId())) {
            throw new AuthorizationException(ErrorCode.HAS_NOT_AUTHORIZATION);
        }
    }

    public boolean isPostWriter() {
        return post.getUser().equals(user);
    }

    public boolean isAuthorized(Long accessUserId) {
        if (accessUserId == null) {
            return false;
        }
        return user.getId().equals(accessUserId);
    }

}