package com.diary.auth.security;

import com.diary.auth.model.User;
import com.diary.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String id) throws UsernameNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UsernameNotFoundException(id + "에 해당하는 사용자가 존재하지 않습니다.");
        }
        return userRepository.findById(id).get();
    }
}
