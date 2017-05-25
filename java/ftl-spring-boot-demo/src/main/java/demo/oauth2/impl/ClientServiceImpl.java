package demo.oauth2.impl;

import demo.oauth2.Client;
import demo.oauth2.ClientService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientServiceImpl implements ClientService {
    @Override
    public Client createClient(Client client) {
        return null;
    }

    @Override
    public Client updateClient(Client client) {
        return null;
    }

    @Override
    public void deleteClient(Long clientId) {

    }

    @Override
    public Client findOne(Long clientId) {
        return null;
    }

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public Client findByClientId(String clientId) {
        return null;
    }

    @Override
    public Client findByClientSecret(String clientSecret) {
        return null;
    }
}
