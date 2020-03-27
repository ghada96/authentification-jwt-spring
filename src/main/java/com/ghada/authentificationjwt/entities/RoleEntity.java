package com.ghada.authentificationjwt.entities;

import javax.persistence.*;

public class RoleEntity {
    /**
     * serial uid par défaut
     */
    private static final long serialVersionUID = 1L;

    /** La constante LENGTH_LABEL. */
    private static final int LENGTH_LABEL = 50;

    /** La constante PRECISION. */
    private static final int PRECISION = 131089;

    /***
     * id role
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROLE")
    @SequenceGenerator(name = "SEQ_ROLE", sequenceName = "sequence_ref_role", allocationSize = 1)
    @Column(name = "id_role", unique = true, nullable = false, precision = PRECISION)
    private Long idRole;

    /**
     * libele du rôle
     */
    @Column(name = "l_role", length = LENGTH_LABEL)
    private String lRole;

    /**
     * libele du rôle
     */
    @Column(name = "abrv_role")
    private String abreviation;

    /**
     * ordre du role
     */
    @Column(name = "ordre_role", precision = PRECISION)
    private Long ordreRole;

    /** actif. */
    @Column(name = "actif")
    private String actif;

    /**
     * Espace.
     */
    @ManyToOne
    @JoinColumn(name = "id_espace")
    private SpaceEntity space;
}
