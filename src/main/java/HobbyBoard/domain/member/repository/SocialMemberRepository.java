package HobbyBoard.domain.member.repository;

import HobbyBoard.domain.member.entity.SocialMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialMemberRepository extends JpaRepository<SocialMember, Long> {
    Optional<SocialMember> findByEmail(String email);
}
