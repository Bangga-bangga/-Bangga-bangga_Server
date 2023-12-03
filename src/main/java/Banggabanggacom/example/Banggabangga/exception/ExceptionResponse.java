package Banggabanggacom.example.Banggabangga.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {
    private int code;
    private String message; // 오류 메시지
    private String details; // 오류 상세정보
}