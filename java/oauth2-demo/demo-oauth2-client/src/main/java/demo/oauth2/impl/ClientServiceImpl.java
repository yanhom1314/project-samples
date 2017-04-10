package demo.oauth2.impl;

import demo.entity.Client;
import demo.oauth2.ClientService;
import demo.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client createClient(Client client) {
        if (!clientRepository.exists(client.getId())) {
            client.setClientId(UUID.randomUUID().toString());
            client.setClientSecret(UUID.randomUUID().toString());
            return clientRepository.save(client);
        } else return null;
    }

    @Override
    public Client updateClient(Client client) {
        if (clientRepository.exists(client.getId()))
            return clientRepository.save(client);
        else return null;
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.delete(id);
    }

    @Override
    public Client findOne(Long id) {
        return clientRepository.findOne(id);
    }

    @Override
    public List<Client> findAll() {
        List<Client> target = new ArrayList<>();
        clientRepository.findAll().iterator().forEachRemaining(target::add);
        return target;
    }

    @Override
    public Client findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId);
    }

    @Override
    public Client findByClientSecret(String clientSecret) {
        return clientRepository.findByClientSecret(clientSecret);
    }
}
