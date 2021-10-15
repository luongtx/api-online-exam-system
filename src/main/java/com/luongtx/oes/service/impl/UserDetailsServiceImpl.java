package com.luongtx.oes.service.impl;

import com.luongtx.oes.constants.ApplicationMessageConstant;
import com.luongtx.oes.entity.User;
import com.luongtx.oes.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException(ApplicationMessageConstant.USER_NAME_IS_NOT_FOUND);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }
}
