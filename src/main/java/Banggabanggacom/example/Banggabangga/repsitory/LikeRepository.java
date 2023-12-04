package Banggabanggacom.example.Banggabangga.repsitory;

import Banggabanggacom.example.Banggabangga.domain.Like;
import Banggabanggacom.example.Banggabangga.domain.Post;
import Banggabanggacom.example.Banggabangga.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    void deleteAllByPost(Post post);

    boolean existsByPostAndUserId(Post post, Long userId);

    Optional<Like> findByPostAndUserId(Post post, Long userId);

    Optional<Like> findByPostAndUser(Post post, User user);
}