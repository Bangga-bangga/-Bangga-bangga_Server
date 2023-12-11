package Banggabanggacom.example.Banggabangga.service;

import Banggabanggacom.example.Banggabangga.domain.User;
import Banggabanggacom.example.Banggabangga.dto.MyPageResponse;
import Banggabanggacom.example.Banggabangga.dto.PostsResponse;
import Banggabanggacom.example.Banggabangga.repsitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostService postService;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public void update(User user, String password) {
        user.update(password);
    }

    public void delete(String email) {
        User user = userRepository.findByEmail(email);
        user.updateStatus("F"); // 회원탈퇴 = F
    }

    public MyPageResponse findUserInfo(Pageable pageable, User user) {
        PostsResponse myPosts = postService.findMyPosts(pageable, user);
        return MyPageResponse.builder()
                .age(user.getAge())
                .category(user.classifyByAge())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .myPost(myPosts).build();
    }
}

