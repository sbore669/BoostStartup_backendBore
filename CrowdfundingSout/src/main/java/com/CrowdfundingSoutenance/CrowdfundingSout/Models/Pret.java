package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Pret extends Investissements {
    private Long idPret;
    private Long Pourcentage;
    private Long pretminimun;
    private Long pretmaximun;

    @ManyToOne
    @JoinColumn(name = "investisseur_id_users")
    private Investisseur investisseur;
}
