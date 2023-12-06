package Banggabanggacom.example.Banggabangga.dto;

import Banggabanggacom.example.Banggabangga.domain.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    Long id;
    Category category;
}
