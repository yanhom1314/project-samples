package demo.repo;

import demo.entity.Client;
import demo.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);

    Long countByUsernameAndClientsContains(String username, Client client);
}
