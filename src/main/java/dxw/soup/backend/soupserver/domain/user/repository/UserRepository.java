package dxw.soup.backend.soupserver.domain.user.repository;

import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.global.common.auth.oauth.OAuth2Provider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findBySocialProviderAndSocialId(OAuth2Provider socialProvider, String socialId);

    boolean existsByEmail(String email);
}