package com.ghada.authentificationjwt.entities;

import com.ghada.authentificationjwt.model.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ref_user")
public class UserEntity {
    @Id
    Long id;

    @Column(name = "nom_user")
    private String firstName;

    /**
     * prenom utilisateur
     */
    @Column(name = "prenom_user")
    private String lastName;

    /**
     * login
     */
    @Column(name = "login_user")
    private String login;

    /**
     * password du user
     */
    @Column(name = "password")
    private String password;


    /**
     * secretKey ( google authenticator ) du user
     */
    @Column(name = "secret_user")
    private String secret;

    /** Le module actif. */
    @Column(name = "actif_user")
    private Long isActif;

    /** Le module actif. */
    @Column(name = "failed_login_attempts")
    private Long failedLoginAttempts;
    private Integer spaceId;












}
