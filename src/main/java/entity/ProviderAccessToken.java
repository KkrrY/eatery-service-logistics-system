package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "access_token")
@Data
public class ProviderAccessToken {
    @Id
    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "token_value")
    private String tokenValue;

    @Column(name = "provider")
    private String provider;
}
