package com.ghada.authentificationjwt.entities;

import javax.persistence.Column;
import javax.persistence.Id;

public class SpaceEntity {
    /**
     * serial uid par défaut
     */
    private static final long serialVersionUID = 1L;

    /***
     * Identifiant d'espace
     */
    @Id
    @Column(name = "id_espace", unique = true, nullable = false)
    private Long id;

    /**
     * libelle d'espace
     */
    @Column(name = "l_espace")
    private String label;

    /**
     * abrev d'espace
     */
    @Column(name = "abrv_espace ")
    private String abrev;

    /**
     * style d'espace
     */
    @Column(name = "style_espace")
    private String style;

    /**
     * ordre
     */
    @Column(name = "ordre_espace")
    private Long order;
}
