package com.ghada.authentificationjwt.dao;

import com.ghada.authentificationjwt.model.UserDTO;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface AuthUserDAO {
    UserDTO findUserByLogin(String login) ;

    UserDTO findUserByLoginWithoutPwd(String login) ;

    Collection<GrantedAuthority> getAuthority(Long userId, Integer space);

    List<Long> getAuthorisedList(Long id);
}
