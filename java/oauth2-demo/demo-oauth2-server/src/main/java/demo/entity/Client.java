package demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "oauth2_client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "client_id", unique = true, nullable = false, length = 64)
    private String clientId;
    @Column(name = "client_name", unique = true, nullable = false, length = 200)
    private String clientName;
    @Column(name = "client_secret", nullable = false, length = 128)
    private String clientSecret;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}