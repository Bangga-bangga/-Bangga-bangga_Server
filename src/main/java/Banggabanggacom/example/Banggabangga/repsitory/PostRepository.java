package Banggabanggacom.example.Banggabangga.repsitory;

import Banggabanggacom.example.Banggabangga.domain.Category;
import Banggabanggacom.example.Banggabangga.domain.Post;
import Banggabanggacom.example.Banggabangga.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {

    String SEARCH_SQL = "SELECT * from post where "
            + "(:query is null or :query = '') "
            + "or "
            + "(title regexp :query) "
            + "or "
            + "(content regexp :query) ";

    Page<Post> findPostsByUserOrderByCreatedAtDesc(Pageable pageable, User user);

    @Query(value = SEARCH_SQL, nativeQuery = true)
    Page<Post> findPostPagesByQuery(Pageable pageable, String query);

    @Query(value = SEARCH_SQL, nativeQuery = true)
    Slice<Post> findPostSlicePagesByQuery(Pageable pageable, String query);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE post SET like_count = like_count + 1 WHERE post_id = :postId", nativeQuery = true)
    void increaseLikeCount(Long postId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE post SET like_count = like_count - 1 WHERE post_id = :postId", nativeQuery = true)
    void decreaseLikeCount(Long postId);

    Page<Post> findPostsByCategory(Category category, Pageable pageable);
}