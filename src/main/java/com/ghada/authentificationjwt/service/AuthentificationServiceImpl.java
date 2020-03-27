package com.ghada.authentificationjwt.service;

import com.ghada.authentificationjwt.dao.AuthUserDAO;
import com.ghada.authentificationjwt.dao.UserDAO;
import com.ghada.authentificationjwt.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service("userService")
public class AuthentificationServiceImpl implements UserDetailsService,AuthentificationService {

    private AuthUserDAO userDAO;


    private UserDAO userDatailsDao;
    @Override
    public UserDTO getUserByLogin(String login) {
        return userDAO.findUserByLogin(login);
    }

    @Override
    public UserDTO getUserByLoginWithoutPwd(String login) {
        return userDAO.findUserByLoginWithoutPwd(login);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("talan".equals(username)) {
            return new User("talan", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }


    @Override
    public Collection<GrantedAuthority> getAuthority(Long userId, Integer space) {
        return userDAO.getAuthority(userId,space);
    }

    @Override
    public Long getUserFailedLoginAttempts(Long id) {
        return userDatailsDao.getFailedLoginAttempts(id);
    }

    @Override
    public void resetUserFailedLoginAttempts(Long id) {
        userDatailsDao.resetUserFailedLoginAttempts(id);

    }

    @Override
    public void setUserInactif(Long id) {
        userDatailsDao.setUserInactif(id);

    }

    @Override
    public void incrementUserFailedLoginAttempts(Long id) {
        userDatailsDao.incrementFailedLoginAttempts(id);


    }



    @Override
    public List<Long> getAuthorisedList(Long id) {
        return userDAO.getAuthorisedList(id);

    }
}
