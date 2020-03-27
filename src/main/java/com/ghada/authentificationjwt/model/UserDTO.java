package com.ghada.authentificationjwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private static final long serialVersionUID = 1456260683387360714L;

    private Long id;
    /**
     * Spring security staff.
     */
    private Collection<GrantedAuthority> authorities;

    /**
     * nom du user
     */
    private String firstName;

    /**
     * prenom du user
     */
    private String lastName;

    /**
     * login de l'utilisateur
     */
    private String login;

    /**
     * passord du user
     */
    private String password;

    /**
     * secret du user
     */
    private String secret ;

    /**
     * groupe du user
     */
    private Long groupeId;

    /** The function id. */
    private Long functionId;

    /**
     * Si le user est activé ou pas.
     */
    private Long isActive;


    private  boolean  isSupport ;


    /** The expired date. */
    private Date expiredDate;

    /**
     * liste des espaces.
     */
    private List<Long> spacesList;

    /** The last log on date. */
    private Date lastLogOnDate;

    /** The login mode. */
    private String[] loginMode;

    /** The email pro. */
    private String emailPro;


    /** The space id. */
    private Integer spaceId;



}
