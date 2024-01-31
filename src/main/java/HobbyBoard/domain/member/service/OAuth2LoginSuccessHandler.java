package HobbyBoard.domain.member.service;

import HobbyBoard.domain.member.dto.CustomUserInfoDto;
import HobbyBoard.domain.member.entity.SocialMember;
import HobbyBoard.domain.member.repository.SocialMemberRepository;
import HobbyBoard.global.jwt.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final SocialMemberRepository socialMemberRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

            String email = (String) oAuth2User.getAttributes().get("email");
            Optional<SocialMember> member = socialMemberRepository.findByEmail(email);
            CustomUserInfoDto dto = CustomUserInfoDto.builder().email(member.get().getEmail())
                    .role(member.get().getRole()).build();
            String accessToken = jwtUtil.createAccessToken(dto);
            response.addHeader("Authorization", "Bearer "+ accessToken);
            response.sendRedirect("http://localhost:8080");
        } catch (Exception e) {
            throw e;
        }

    }
}
