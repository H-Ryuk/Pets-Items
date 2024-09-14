package com.hassan.pets.Config;

import com.hassan.pets.Exception.TargetNotFoundException;
import com.hassan.pets.Repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CostomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public CostomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("this is the user name : " + username);

        return userRepo.findByEmail(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new TargetNotFoundException("User", 3L));
    }


}
