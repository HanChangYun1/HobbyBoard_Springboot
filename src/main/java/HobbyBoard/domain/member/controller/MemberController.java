package HobbyBoard.domain.member.controller;

import HobbyBoard.domain.member.dto.LoginRequestDto;
import HobbyBoard.domain.member.dto.SignupRequestDto;
import HobbyBoard.domain.member.entity.LocalMember;
import HobbyBoard.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequestDto request
    ){
        String token = this.memberService.login(request);
        return ResponseEntity.status(HttpStatus.OK).header("Authorization", "Bearer "+ token).build();
    }

//    @GetMapping("/naver/callback")
//    public void handleNaverLogin(Principal principal){
//        if (principal instanceof OAuth2AuthenticationToken) {
//            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) principal;
//            log.info("OAuth2 Login Success! User: {}", oauthToken.getPrincipal());
//        } else {
//            log.info("OAuth2 Login Failed. No valid OAuth2 authentication token found.");
//        }
//    }

    @PostMapping("signup")
    public ResponseEntity<LocalMember> signup(
            @Valid @RequestBody SignupRequestDto request
    ){
        LocalMember member = this.memberService.signup(request);
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }
}

