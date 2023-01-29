package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Typeprojet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Idtypeprojets;
    private String nomtype;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "typeprojet_investisseur",
            joinColumns = @JoinColumn(name = "typeprojet_id"),
            inverseJoinColumns = @JoinColumn(name = "investisseur_id"))
    private Set<Investisseur> investisseurs = new HashSet<>();


}
