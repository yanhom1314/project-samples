package demo.spring;

import demo.entity.User;
import demo.entity.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserCache {
    @Autowired
    private UserRepository userRepository;

    @Cacheable(value = CacheConfig.CODE_ALL_USER)
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        System.out.println(" getAll:" + list.size());
        return list;
    }

    @CachePut(value = CacheConfig.CODE_ALL_USER)
    public List<User> updateAll() {
        List<User> list = new ArrayList<>();

        System.out.println(" updateAll:" + list.size());
        return list;
    }
}
