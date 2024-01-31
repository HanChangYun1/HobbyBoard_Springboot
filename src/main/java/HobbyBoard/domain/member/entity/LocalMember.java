package HobbyBoard.domain.member.entity;

import HobbyBoard.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
public class LocalMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;





    //== 연관관계 메서드 ==//


    @Builder
    public LocalMember(String email, String password, String nickname, Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

//    public LocalUser update(MemberInfoDto dto){
//        this.name = dto.getName();
//        this.picture = dto.getPicture();
//
//        return this;
//    }



    public String getRoleKey(){
        return this.role.getKey();
    }
}
