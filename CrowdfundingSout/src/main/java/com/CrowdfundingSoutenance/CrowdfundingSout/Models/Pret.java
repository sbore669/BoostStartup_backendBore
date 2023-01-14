package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Pret extends Investissements {
    private Long Pourcentage;
    private Double retourinvestissement;



    @ManyToOne
    @JoinColumn(name = "investisseur_id_users")
    private Investisseur investisseur;
}
