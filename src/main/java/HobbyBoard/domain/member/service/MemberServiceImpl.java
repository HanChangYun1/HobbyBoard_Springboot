package HobbyBoard.domain.member.service;

import HobbyBoard.domain.member.dto.CustomUserInfoDto;
import HobbyBoard.domain.member.dto.LoginRequestDto;
import HobbyBoard.domain.member.dto.SignupRequestDto;
import HobbyBoard.domain.member.entity.LocalMember;
import HobbyBoard.domain.member.entity.Role;
import HobbyBoard.domain.member.repository.LocalMemberRepository;
import HobbyBoard.global.jwt.JwtUtil;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final LocalMemberRepository localMemberRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;


    @Transactional
    @Override
    public String login(LoginRequestDto request){
        Optional<LocalMember> localMember = localMemberRepository.findLocalMemberByEmail(request.getEmail());
        if(localMember.isEmpty()){
            throw new UsernameNotFoundException("존재하지 않는 email 입니다.");
        }
        if(!encoder.matches(request.getPassword(), localMember.get().getPassword())){
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        CustomUserInfoDto dto = CustomUserInfoDto.builder().email(localMember.get().getEmail())
                .role(localMember.get().getRole()).build();
        String accessToken = jwtUtil.createAccessToken(dto);
        return accessToken;
    }

    @Transactional
    @Override
    public LocalMember signup(SignupRequestDto request){
        Optional<LocalMember> validMember = localMemberRepository.findLocalMemberByEmail(request.getEmail());
        if(validMember.isPresent()){
            throw new ValidationException("This Email is already exist." + request.getEmail());
        }
        String encodedPassword = encoder.encode(request.getPassword());
        LocalMember member = LocalMember.builder().email(request.getEmail())
                                .password(encodedPassword)
                                .nickname(request.getNickname())
                                .role(Role.USER)
                                .build();
        LocalMember savedMember = localMemberRepository.save(member);
        return savedMember;
    }

}
