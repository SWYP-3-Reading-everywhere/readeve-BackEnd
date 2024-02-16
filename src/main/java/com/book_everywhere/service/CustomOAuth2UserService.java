package com.book_everywhere.service;

import com.book_everywhere.jwt.CustomOAuth2User;
import com.book_everywhere.web.dto.jwt.KakaoResponse;
import com.book_everywhere.web.dto.jwt.OAuth2Response;
import com.book_everywhere.web.dto.user.UserDto;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        KakaoResponse kakaoResponse = null;
        try{
            //user 정보받기
            OAuth2User oAuth2User = super.loadUser(userRequest);

            System.out.println("oAuth2User = " + oAuth2User);

            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            kakaoResponse = new KakaoResponse(oAuth2User.getAttributes());
        } catch(Exception exception) {
            System.out.println("에러뜸");
        }

        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듬
        String providerId = kakaoResponse.getProvider()+" "+kakaoResponse.getProviderId();

        UserDto userDto = new UserDto();
        userDto.setSocialId(providerId);
        userDto.setNickname(kakaoResponse.getNickname());
        userDto.setRole("ROLE_USER");

        return new CustomOAuth2User(userDto);
    }
}
