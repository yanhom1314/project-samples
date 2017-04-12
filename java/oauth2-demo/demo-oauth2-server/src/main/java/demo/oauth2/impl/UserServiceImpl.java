package demo.oauth2.impl;

import demo.entity.User;
import demo.oauth2.UserService;
import demo.repo.ClientRepository;
import demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public User createUser(User user) {
        if (user.getId() == null || !userRepository.exists(user.getId())) {
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        if (user.getId() != null && userRepository.exists(user.getId())) {
            userRepository.save(user);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    public void changePassword(Long id, String newPassword) {
        if (userRepository.exists(id)) {
            User user = userRepository.findOne(id);
            user.setPassword(newPassword);
            userRepository.save(user);
        }
    }

    @Override
    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean isFirst(String username, String clientId) {
        return userRepository.countByUsernameAndClientsContains(username, clientRepository.findByClientId(clientId)) <= 0;
    }
}
