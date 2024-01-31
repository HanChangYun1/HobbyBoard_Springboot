package HobbyBoard.domain.member.dto;

import HobbyBoard.domain.member.entity.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomUserInfoDto {
    private String email;
    private String nickname;
    private Role role;

    @Builder
    public CustomUserInfoDto(String email, Role role) {
        this.email = email;
        this.role = role;
    }
}
