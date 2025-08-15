package dxw.soup.backend.soupserver.global.common.auth.oauth.userinfo;

import dxw.soup.backend.soupserver.global.common.auth.oauth.OAuth2Provider;
import dxw.soup.backend.soupserver.global.common.auth.oauth.OAuth2UserInfo;
import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo create(String registrationId, Map<String, Object> attributes) {
        switch (OAuth2Provider.byRegistrationId(registrationId)) {
            case KAKAO -> {
                return KakaoOAuth2UserInfo.from(attributes);
            }
            case NAVER -> {
                return NaverOAuth2UserInfo.from(attributes);
            }
            default -> {
                return null;
            }
        }
    }
}
