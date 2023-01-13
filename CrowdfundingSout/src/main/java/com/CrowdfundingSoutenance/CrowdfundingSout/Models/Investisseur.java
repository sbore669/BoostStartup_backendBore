package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Data
public class Investisseur extends Utilisateurs{
    private Long totalInvestissement;
    private Long telephone;


    @ManyToMany(mappedBy = "investisseurs")
    private Set<Typeprojet> typeprojet;


}

