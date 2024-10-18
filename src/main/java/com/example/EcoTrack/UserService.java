
package com.example.EcoTrack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User us) {
        return userRepository.save(us);
    }

    public List<User> findByEmail(String userId) {
        return userRepository.findByEmail(userId);
    }
    public List<User> findall()
    {
        return userRepository.findAll();
    }

    
}
