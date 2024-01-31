package HobbyBoard.domain.member.repository;

import HobbyBoard.domain.member.entity.LocalMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalMemberRepository extends JpaRepository<LocalMember, Long> {

    Optional<LocalMember> findLocalMemberByEmail(String email);
}
