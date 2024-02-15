package com.book_everywhere.config.auth;

import com.book_everywhere.domain.user.User;
import com.book_everywhere.domain.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    /**
     * loadUser 메서드에서는 DefaultOAuth2UserService를 사용하여 OAuth2UserRequest에 대한 OAuth2User 객체를 로드합니다.
     * 그러면 로그인하는 사용자의 공급자 등록 ID 및 사용자 이름 속성 이름과 같은 사용자 정보를 가져와서 OAuthAttributes 객체로 변환합니다.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", user);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("MEMBER")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    /**
     *  UserRepository 를 사용하여 OAuthAttributes 에서 가져온
     *  사용자 정보를 데이터베이스에 저장하거나 업데이트합니다.
     */
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByNickname(attributes.getNickname())
                .map(entity -> entity.update(attributes.getNickname(),attributes.getImage()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}