package HobbyBoard.domain.member.service;

import HobbyBoard.domain.member.dto.CustomUserInfoDto;
import HobbyBoard.domain.member.entity.SocialMember;
import HobbyBoard.domain.member.repository.SocialMemberRepository;
import HobbyBoard.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final SocialMemberRepository socialMemberRepository;
    private final JwtUtil jwtUtil;
    private final HttpServletResponse response;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try{
            OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
            log.info("delegate:{}", delegate);
            OAuth2User oAuth2User = delegate.loadUser(userRequest);


            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                    .getUserInfoEndpoint().getUserNameAttributeName();


            log.info("oAuth2User:{}", oAuth2User.getAttributes());
            OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
            log.info("attributes:{}", attributes);

            SocialMember member = findOrSave(attributes);

            CustomUserInfoDto dto = CustomUserInfoDto.builder().email(member.getEmail())
                    .role(member.getRole()).build();
            String accessToken = jwtUtil.createAccessToken(dto);
            response.addHeader("Authorization", "Bearer "+ accessToken);



            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                    attributes.getAttributes(),
                    attributes.getNameAttributeKey()
            );
        }catch (Exception e){
            throw new OAuth2AuthenticationException(String.valueOf(e));
        }
    }

    @Transactional
    public SocialMember findOrSave(OAuthAttributes attributes) {
        SocialMember member = socialMemberRepository.findByEmail(attributes.getEmail())
                .orElse(attributes.toEntity());
        return socialMemberRepository.save(member);
    }
}
