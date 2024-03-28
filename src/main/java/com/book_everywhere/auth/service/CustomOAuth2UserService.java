package com.book_everywhere.auth.service;

import com.book_everywhere.auth.dto.CustomOAuth2User;
import com.book_everywhere.auth.dto.OAuthAttributes;
import com.book_everywhere.auth.entity.Role;
import com.book_everywhere.auth.entity.User;
import com.book_everywhere.auth.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * loadUser 메서드에서는 DefaultOAuth2UserService를 사용하여 OAuth2UserRequest에 대한 OAuth2User 객체를 로드합니다.
     * 그러면 로그인하는 사용자의 공급자 등록 ID 및 사용자 이름 속성 이름과 같은 사용자 정보를 가져와서 OAuthAttributes 객체로 변환합니다.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", user);

        // UserDetails 객체 생성 또는 조회
        UserDetails userDetails = customUserDetailsService.loadUserBySocialId(user.getSocialId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new CustomOAuth2User(attributes,Role.ROLE_MEMBER);
    }

    /**
     *  UserRepository 를 사용하여 OAuthAttributes 에서 가져온
     *  사용자 정보를 데이터베이스에 저장하거나 업데이트합니다.
     */
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findBySocialId(attributes.getSocialId())
                .map(entity -> entity.update(attributes.getNickname(),attributes.getImage(), Role.ROLE_MEMBER))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}