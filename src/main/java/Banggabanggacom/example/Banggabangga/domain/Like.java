package Banggabanggacom.example.Banggabangga.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    protected Like() {
    }

    @Builder
    private Like(Post post, User user) {
        this.post = post;
        this.user = user;
        post.addPostLike(this);
    }

    public boolean isLikeOf(Long memberId) {
        return user.hasId(memberId);
    }

    public void delete() {
        this.post = null;
    }
}