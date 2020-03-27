package com.ghada.authentificationjwt.dao.impl;

import com.ghada.authentificationjwt.dao.AuthUserDAO;
import com.ghada.authentificationjwt.entities.RoleEntity;
import com.ghada.authentificationjwt.entities.UserEntity;
import com.ghada.authentificationjwt.model.UserDTO;
import com.ghada.authentificationjwt.utilitiy.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class AuthUserDAOImpl implements AuthUserDAO {

    @Autowired
    private EntityManager em;

    private static final String LOGIN = "login";
    @SuppressWarnings("unchecked")
    @Override
    public UserDTO findUserByLogin(String login) {
        Query query = em.createQuery("from UserEntity where login = :login");
        query = query.setParameter(LOGIN, login);
        List<UserEntity> result = query.getResultList();
        return (null == result || result.isEmpty()) ? null : getDTOFromEntity(result.get(0));

    }
    public UserDTO getDTOFromEntity(UserEntity entity) {
        UserDTO userDTO = UserUtility.convertEntityToDTO(entity);
        userDTO.setPassword(entity.getPassword());
        return userDTO;
    }

    @Override
    public UserDTO findUserByLoginWithoutPwd(String login) {
        Query query = em.createQuery("from UserEntity  where login = :login");
        query = query.setParameter(LOGIN, login);
        List<UserEntity> result = query.getResultList();
        return (null == result || result.isEmpty()) ? null : getDTOFromEntityWithoutPwd(result.get(0));
    }
    public UserDTO getDTOFromEntityWithoutPwd(UserEntity entity) {
        return UserUtility.convertEntityToDTO(entity);
    }

    @Override
    public Collection<GrantedAuthority> getAuthority(Long userId, Integer space) {
        Query query;
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (userId != null) {
            StringBuilder queryBuilder = new StringBuilder("select distinct r from RoleEntity r, AdminRoleUserEntity a  where  r.id = a.role.id and a.user.id = :user and a.societe = ");

            if (space != null) {
                queryBuilder.append(" and r.space.id= :space");
            }
            query = em.createQuery(queryBuilder.toString());
            query = query.setParameter("user", userId);

            if (space != null) {
                query = query.setParameter("space", Long.valueOf(space));
            }

            @SuppressWarnings("unchecked")
            List<RoleEntity> functions = query.getResultList();
            for (RoleEntity fonctionEntity : functions) {

                authorities.add(new SimpleGrantedAuthority("ROLE_"+fonctionEntity.getAbreviation().toUpperCase()));
            }
        }
        return authorities;
    }



    @Override
    public List<Long> getAuthorisedList(Long id) {
        Query query = em.createNativeQuery(
                "select distinct espace.id_espace "
                        + " from prm_espace espace ,  ref_role r , admin_role_user a  where "
                        + " r.id_espace= espace.id_espace and  r.id_role = a.id_role and a.id_user = :idUSer" );
        query = query.setParameter("idUser", id);

        @SuppressWarnings("unchecked")
        List<Object> spacesList = (query.getResultList());
        return spacesList.stream().map(space ->((BigDecimal)space).longValue()).collect(Collectors.toList());
    }
}
