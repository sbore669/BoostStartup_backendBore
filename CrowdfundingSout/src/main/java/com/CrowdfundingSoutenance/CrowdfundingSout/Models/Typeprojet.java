package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Typeprojet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Idtypeprojets;
    private String nomtype;


    @ManyToMany
    @JoinTable(name = "typeprojet_utilisateur",
            joinColumns = @JoinColumn(name = "typeprojet_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id"))
    private Set<Utilisateurs> utilisateurs;


}
