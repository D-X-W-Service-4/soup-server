package dxw.soup.backend.soupserver.global.common.auth.oauth.handler;

import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.repository.UserRepository;
import dxw.soup.backend.soupserver.global.common.auth.UserPrincipal;
import dxw.soup.backend.soupserver.global.common.auth.oauth.OAuth2Provider;
import dxw.soup.backend.soupserver.global.common.auth.oauth.OAuth2UserInfo;
import dxw.soup.backend.soupserver.global.common.auth.service.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;

    @Value("${service.oauth.redirect-uri}")
    private String oauthRedirectUri;

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        OAuth2UserInfo oAuth2UserInfo = userPrincipal.getOAuth2UserInfo();
        String socialId = oAuth2UserInfo.getSocialId();
        OAuth2Provider provider = oAuth2UserInfo.getProvider();
        String email = oAuth2UserInfo.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent() && userOptional.get().getSocialProvider() != provider) {
            getRedirectStrategy().sendRedirect(request, response, getRedirectUrl(oauthRedirectUri, CallbackType.DUPLICATE_EMAIL, null));
            return;
        }

        User user = userRepository.findBySocialProviderAndSocialId(provider, socialId)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(email)
                            .socialProvider(provider)
                            .socialId(socialId)
                            .build();
                    return userRepository.save(newUser);
                });

        String accessToken = tokenProvider.generateToken(user, Duration.ofDays(30));
        CallbackType type = (user.getNickname() == null) ? CallbackType.NEW_USER : CallbackType.SUCCESS;

        getRedirectStrategy().sendRedirect(request, response, getRedirectUrl(oauthRedirectUri, type, accessToken));
    }

    private String getRedirectUrl(String targetUrl, CallbackType type, String token) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("type", type);

        if (token != null) {
            uriBuilder.queryParam("token", token);
        }

        return uriBuilder.build().toUriString();
    }

    enum CallbackType {
        SUCCESS, NEW_USER, DUPLICATE_EMAIL
    }
}