package Banggabanggacom.example.Banggabangga.dto;

import Banggabanggacom.example.Banggabangga.domain.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyPageResponse {
    private String email;
    private Category category;
    private String nickname;
    private int age;
    private PostsResponse myPost;

}
