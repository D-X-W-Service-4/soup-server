package dxw.soup.backend.soupserver.global.common.auth.oauth;

import java.util.Map;

public interface OAuth2UserInfo {
    String getEmail();
    String getName();
    OAuth2Provider getProvider();
    String getSocialId();
    Map<String, Object> getAttributes();

    String FALLBACK_EMAIL = "test@example.com";
    String FALLBACK_NAME = "test";
}
