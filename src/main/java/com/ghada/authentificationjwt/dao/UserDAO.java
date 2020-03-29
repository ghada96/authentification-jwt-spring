package com.ghada.authentificationjwt.dao;

import javax.transaction.Transactional;

public interface UserDAO {
    public Boolean isUserActif(Long persId);
    Long getFailedLoginAttempts(Long id) ;
    void resetUserFailedLoginAttempts(Long id);
    void incrementFailedLoginAttempts(Long id);


    @Transactional
    void setUserInactif(Long id);

}
