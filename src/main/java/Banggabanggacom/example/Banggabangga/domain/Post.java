package Banggabanggacom.example.Banggabangga.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String content;

    private int likeCount = 0;

    private String writerNickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    protected Post() {
    }

    @Builder
    private Post(String title, String content, User user, String writerNickname, List<Like> likes,
                 List<Comment> comments, Category category) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.likes = likes;
        this.writerNickname = writerNickname;
        this.comments = comments;
        this.category = category;
    }

    public boolean isModified() {
        return !createdAt.equals(modifiedAt);
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public boolean isOwner(Long accessUserId) {
        if (accessUserId == null) {
            return false;
        }
        return user.getId().equals(accessUserId);
    }

    public int getCommentCount() {
        if (comments == null) {
            return 0;
        }
        return comments.size();
    }

    public void addPostLike(Like like) {
        likes.add(like);
    }

    public void deleteLike(Like like) {
        likes.remove(like);
        like.delete();
    }

}