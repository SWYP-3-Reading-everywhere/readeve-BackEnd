package com.book_everywhere.auth.service;

import com.book_everywhere.auth.entity.User;
import com.book_everywhere.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserBySocialId(Long socialId) {
        User user = userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with social ID: " + socialId));

        return new org.springframework.security.core.userdetails.User(
                user.getNickname(),
                "", // 소셜 로그인이므로 비밀번호는 사용하지 않음
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))); // 권한 설정
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("loadUserByUsername is not supported");
    }
}
