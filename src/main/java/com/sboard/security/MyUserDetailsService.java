package com.sboard.security;

import com.sboard.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.sboard.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 사용자가 입력한 아이디로 사용자 조회, 비밀번호에 대한 검증은 이전 컴포넌트인 AuthenticationProvider에서 수행
        Optional<User> optUser = userRepository.findById(username);

        if(optUser.isPresent()) {
            // 시큐리티 사용자 인증객체 생성 후 반환
            MyUserDetails myUserDetails = MyUserDetails.builder()
                                                                .user(optUser.get())
                                                                .build();
            return myUserDetails;
        }
        // 사용자가 입력한 아이디가 DB에 존재하지 않을 경우
        return null;
    }
}
