package repository;

import entity.ProviderAccessToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProviderAccessTokenRepository extends CrudRepository<ProviderAccessToken, String> {
    Optional<ProviderAccessToken> findByUserEmail (String email);
    void deleteByTokenValue (String tokenValue);
}
