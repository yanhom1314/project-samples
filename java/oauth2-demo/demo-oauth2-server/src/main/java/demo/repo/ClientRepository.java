package demo.repo;

import demo.entity.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {
    Client findByClientId(String clientId);

    Client findByClientName(String clientName);

    Client findByClientSecret(String clientSecret);
}
