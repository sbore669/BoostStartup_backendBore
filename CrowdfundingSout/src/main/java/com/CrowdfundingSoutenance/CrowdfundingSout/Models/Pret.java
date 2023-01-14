package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pret extends Investissements {
    private Long Pourcentage;
    private Double retourinvestissement;



    @ManyToOne
    @JoinColumn(name = "investisseur_id_users")
    private Investisseur investisseur;
}
