package HobbyBoard.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
public class SocialMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column
    private String service;

    @Column
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;





    //== 연관관계 메서드 ==//


    @Builder
    public SocialMember(String email, String service, String nickname, Role role) {
        this.email = email;
        this.service = service;
        this.nickname = nickname;
        this.role = role;
    }


//    public SocialUser update(MemberInfoDto dto){
//        this.name = dto.getName();
//        this.picture = dto.getPicture();
//
//        return this;
//    }



    public String getRoleKey(){
        return this.role.getKey();
    }
}
