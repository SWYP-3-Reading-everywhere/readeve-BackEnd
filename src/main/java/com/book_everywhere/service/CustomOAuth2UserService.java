package com.book_everywhere.service;

import com.book_everywhere.domain.user.Role;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.domain.user.UserRepository;
import com.book_everywhere.jwt.CustomOAuth2User;
import com.book_everywhere.web.dto.jwt.KakaoResponse;
import com.book_everywhere.web.dto.jwt.OAuth2Response;
import com.book_everywhere.web.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

            //user 정보받기
            OAuth2User oAuth2User = super.loadUser(userRequest);

            KakaoResponse oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());

        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듬
        String socialId = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        User findUser = userRepository.findBySocialId(socialId);

        if(findUser == null) {

            User user = User.builder()
                    .nickname(oAuth2Response.getNickname())
                    .imageUrl(oAuth2Response.getImageUrl())
                    .socialId(socialId)
                    .Role(Role.MEMBER)
                    .build();

            userRepository.save(user);

            UserDto userDto = new UserDto();
            userDto.setSocialId(socialId);
            userDto.setNickname(oAuth2Response.getNickname());
            userDto.setImageUrl(oAuth2Response.getImageUrl());
            userDto.setRole("MEMBER");

            return new CustomOAuth2User(userDto);
        }
        else {

            UserDto userDto = new UserDto();
            userDto.setSocialId(socialId);
            userDto.setNickname(findUser.getNickname());
            userDto.setImageUrl(findUser.getImageUrl());
            userDto.setRole("MEMBER");

            return new CustomOAuth2User(userDto);
        }
    }
}
