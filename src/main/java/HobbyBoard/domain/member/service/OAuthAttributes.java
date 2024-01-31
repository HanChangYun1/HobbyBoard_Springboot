package HobbyBoard.domain.member.service;

import HobbyBoard.domain.member.entity.Role;
import HobbyBoard.domain.member.entity.SocialMember;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private final String id;
    private final String email;
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String picture;
    private String service;

    @Builder
    public OAuthAttributes(String id,Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture, String service){
        this.id = id;
        this.email = email;
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.picture = picture;
        this.service = service;
    }


    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("google".equals(registrationId)) {
            return ofGoogle(registrationId,userNameAttributeName, attributes);
        }else if ("naver".equals(registrationId)){
            return ofNaver(registrationId,userNameAttributeName, attributes);
        }else if ("kakao".equals(registrationId)){
            return ofKakao(registrationId,userNameAttributeName, attributes);
        }


        throw new IllegalArgumentException("Unsupported provider: " + registrationId);
    }



    public static OAuthAttributes ofGoogle(String registrationId ,String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .id((String) attributes.get("sub"))
                .name((String) attributes.get("name"))
                .service(registrationId)
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes ofNaver(String registrationId ,String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .id((String) attributes.get("sub"))
                .name((String) attributes.get("name"))
                .service(registrationId)
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes ofKakao(String registrationId ,String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .id((String) attributes.get("sub"))
                .name((String) attributes.get("name"))
                .service(registrationId)
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }



    public SocialMember toEntity(){
        return SocialMember.builder()
                .nickname("게스트")
                .service(service)
                .email(email)
                .role(Role.USER)
                .build();
    }

}
