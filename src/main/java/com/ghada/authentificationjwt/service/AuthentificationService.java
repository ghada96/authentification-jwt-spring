package com.ghada.authentificationjwt.service;

import com.ghada.authentificationjwt.model.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public interface AuthentificationService {
    UserDTO getUserByLogin(String login);
    UserDTO getUserByLoginWithoutPwd(String login);
    UserDetails loadUserByUsername(String username);

    Collection<GrantedAuthority> getAuthority(Long userId, Integer space);

    Long getUserFailedLoginAttempts(Long id);

    void resetUserFailedLoginAttempts(Long id);

    void setUserInactif(Long id);

    void incrementUserFailedLoginAttempts(Long id) ;

   // int getIdEspaceByLabel(String label) ;

    //List<FonctionDTO> getIdfromLabelAuthority(List<String>  label, Long spaceId , Long socId) throws ByblosException ;

    List<Long> getAuthorisedList(  Long id) ;

}
