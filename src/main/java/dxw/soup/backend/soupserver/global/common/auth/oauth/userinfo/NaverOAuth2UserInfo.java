package dxw.soup.backend.soupserver.global.common.auth.oauth.userinfo;

import dxw.soup.backend.soupserver.global.common.auth.oauth.OAuth2Provider;
import dxw.soup.backend.soupserver.global.common.auth.oauth.OAuth2UserInfo;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "from")
public class NaverOAuth2UserInfo implements OAuth2UserInfo {
    @Getter
    private final Map<String, Object> attributes;

    public HashMap<String, String> getResponse() {
        return (HashMap) attributes.get("response");
    }

    @Override
    public String getEmail() {
        return getResponse().getOrDefault("email", FALLBACK_EMAIL);
    }

    @Override
    public String getName() {
        return getResponse().getOrDefault("name", FALLBACK_NAME);
    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.NAVER;
    }

    @Override
    public String getSocialId() {
        return getResponse().getOrDefault("id", "-1");
    }
}
