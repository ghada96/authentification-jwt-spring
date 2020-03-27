package com.ghada.authentificationjwt.dao.impl;

import com.ghada.authentificationjwt.dao.UserDAO;
import com.ghada.authentificationjwt.entities.UserEntity;
import com.ghada.authentificationjwt.model.UserDTO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class UserDAOImpl  implements UserDAO {
    EntityManager em;
    @Override
    public Boolean isUserActif(Long persId) {
        if (persId == null) {
            return Boolean.FALSE;
        }
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(" select actif_user = 1 ");
        queryBuilder.append(" from byblos.ref_user");
        queryBuilder.append(" where id_pers = :persId and is_support=false");

        List<Boolean> result = em.createNativeQuery(queryBuilder.toString())
                .setParameter("persId", persId).getResultList();

        return result.stream().findFirst().orElse(false);
    }

    @Override
    public Long getFailedLoginAttempts(Long id) {
        StringBuilder query = new StringBuilder();
        query.append(	 "select failed_login_attempts " +
                "			from byblos.ref_user   " +
                "			where id_user =:id_user");
        Query query1 = em.createNativeQuery(query.toString());
        query1.setParameter("id_user", id);

        Object result = query1.getSingleResult();
        if( result instanceof BigDecimal){
            return ((BigDecimal) result).longValue();
        }
        else if ( result instanceof BigInteger){
            return ((BigInteger) result).longValue();
        }
        else return 0l;
    }

    @Override
    @Transactional
    public void resetUserFailedLoginAttempts(Long id) {
        StringBuilder query = new StringBuilder();
        query.append("update byblos.ref_user set failed_login_attempts =:value	where id_user = :id_user");

        Query query1 = em.createNativeQuery(query.toString());
        query1.setParameter("value", 0L);
        query1.setParameter("id_user", id).executeUpdate();


    }
    @Override
    @Transactional
    public void setUserInactif(Long id) {
        StringBuilder query = new StringBuilder();
        query.append("update byblos.ref_user set actif_user =:value where id_user =:id_user" );

        Query query1 = em.createNativeQuery(query.toString());
        query1.setParameter("value", 0L);
        query1.setParameter("id_user", id).executeUpdate();

    }

    @Override
    @Transactional
    public void incrementFailedLoginAttempts(Long id) {
        Long failedLoginAttempts = getFailedLoginAttempts(id);
        StringBuilder query = new StringBuilder();
        query.append("update byblos.ref_user set failed_login_attempts = :value where id_user = :id_user" );
        Query query1 = em.createNativeQuery(query.toString());
        query1.setParameter("value",failedLoginAttempts + 1);
        query1.setParameter("id_user", id).executeUpdate();

    }
}
