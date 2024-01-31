package HobbyBoard.domain.member.service;

import HobbyBoard.domain.member.dto.LoginRequestDto;
import HobbyBoard.domain.member.dto.SignupRequestDto;
import HobbyBoard.domain.member.entity.LocalMember;

public interface MemberService  {

    public abstract String login(LoginRequestDto request);

    public abstract LocalMember signup(SignupRequestDto request);
}
