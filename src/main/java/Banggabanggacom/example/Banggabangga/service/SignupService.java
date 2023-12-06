package Banggabanggacom.example.Banggabangga.service;

import Banggabanggacom.example.Banggabangga.domain.User;
import Banggabanggacom.example.Banggabangga.dto.UserSignupRequest;
import Banggabanggacom.example.Banggabangga.exception.ErrorCode;
import Banggabanggacom.example.Banggabangga.exception.User.SignupException;
import Banggabanggacom.example.Banggabangga.repsitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserRepository userRepository;


    public User signup(UserSignupRequest request) {
        checkDuplicateNickName(request.getNickname());
        User user = creatUser(request);
        return userRepository.save(user);
    }

    public void checkDuplicateNickName(String nickname) {
        if (userRepository.existsUserByNickname(nickname)) {
            throw new SignupException(ErrorCode.DUPLICATED_NICKNAME);
        }
    }

    private User creatUser(UserSignupRequest request) {
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .age(request.getAge())
                .createAt(LocalDateTime.now())
                .role("ROLE_USER")
                .status("A").build();
    }
}

