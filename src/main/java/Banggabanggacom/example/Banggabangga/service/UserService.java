package Banggabanggacom.example.Banggabangga.service;

import Banggabanggacom.example.Banggabangga.domain.User;
import Banggabanggacom.example.Banggabangga.repsitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByNickname(email);
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public void update(User user, String password) {
        user.update(password);
    }

    public void delete(String email) {
        User user = userRepository.findByNickname(email);
        user.updateStatus("F"); // 회원탈퇴 = F
    }

}
