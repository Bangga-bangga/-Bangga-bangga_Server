package Banggabanggacom.example.Banggabangga.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //id
    @Column(length = 100, nullable = false, unique = true)
    private String email;
    //psw
    @Column(length = 100, nullable = false)
    private String password;
    // 닉네임
    @Column(length = 50, nullable = false)
    private String nickname;
    //나이
    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column()
    private LocalDateTime updateAt;

    @Column
    private String role;

    // 비밀번호 수정
    public void updatePassword(String password){
        this.password = password;
    }

    // 회원정보 수정
    public void update(String password){
        this.password = password;
        this.updateAt = LocalDateTime.now();
    }

    // 회원 상태 수정
    public void updateStatus(String status){
        if(status.equals("A")){    //활동 중
            this.status = "A";
        } else if (status.equals("B")) {    //비활성화
            this.status = "B";
        } else if (status.equals("F")) { //회원 탈퇴
            this.status = "F";
        }
    }

    public boolean hasId(Long id) {
        return this.id.equals(id);
    }

    public Category classifyByAge() {
        if (age > 40) {
            return Category.ADULT;
        }

        return Category.MZ;
    }
}