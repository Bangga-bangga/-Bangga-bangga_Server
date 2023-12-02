package Banggabanggacom.example.Banggabangga.config.auth;

import Banggabanggacom.example.Banggabangga.domain.User;
import Banggabanggacom.example.Banggabangga.repsitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// UserDetailsService는 DaoAuthenticationProvider와 협력하는 인터페이스
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService : 진입");
        User user = userRepository.findByNickname(username);

        return new PrincipalDetails(user);    // 여기서 return 되면 session 에 들어감 -> 권한 관리를 위해서만 사용됨
    }
}