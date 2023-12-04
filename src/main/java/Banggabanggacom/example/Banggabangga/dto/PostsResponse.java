package Banggabanggacom.example.Banggabangga.dto;

import Banggabanggacom.example.Banggabangga.domain.Post;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostsResponse {
    private List<PreviewPostsResponse> posts;
    private int totalPageCount;

    public PostsResponse() {
    }

    public PostsResponse(List<PreviewPostsResponse> posts, int totalPageCount) {
        this.posts = posts;
        this.totalPageCount = totalPageCount;
    }

    public static PostsResponse of(List<Post> posts, int totalPageCount) {
        List<PreviewPostsResponse> postsElementResponses = posts.stream()
                .map(PreviewPostsResponse::from)
                .collect(Collectors.toList());
        return new PostsResponse(postsElementResponses, totalPageCount);
    }

}
