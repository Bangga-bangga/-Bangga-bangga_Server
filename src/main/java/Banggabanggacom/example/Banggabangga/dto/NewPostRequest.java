package Banggabanggacom.example.Banggabangga.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class NewPostRequest {

    @NotBlank(message = "제목은 1자 이상 50자 이하여야 합니다.")
    private String title;
    @NotBlank(message = "본문은 1자 이상 5000자 이하여야 합니다.")
    private String content;

    public NewPostRequest() {
    }

    public NewPostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}