package dxw.soup.backend.soupserver.global.common.auth.oauth.userinfo;

import dxw.soup.backend.soupserver.global.common.auth.oauth.OAuth2Provider;
import dxw.soup.backend.soupserver.global.common.auth.oauth.OAuth2UserInfo;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "from")
public class KakaoOAuth2UserInfo implements OAuth2UserInfo {
    @Getter
    private final Map<String, Object> attributes;

    private HashMap<String, String> getKakaoAccount() {
        return (HashMap) attributes.get("kakao_account");
    }

    private HashMap<String, String> getProperties() {
        return (HashMap) attributes.get("properties");
    }

    @Override
    public String getEmail() {
        return getKakaoAccount().getOrDefault("email", FALLBACK_EMAIL);
    }

    @Override
    public String getName() {
        return getProperties().getOrDefault("nickname", FALLBACK_NAME);
    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.KAKAO;
    }

    @Override
    public String getSocialId() {
        return attributes.get("id").toString();
    }
}
