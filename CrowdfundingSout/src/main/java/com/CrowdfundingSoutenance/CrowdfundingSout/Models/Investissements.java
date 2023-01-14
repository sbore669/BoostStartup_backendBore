package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Investissements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id_invest;
    private Long montantInvest;
    private Date date_investissement;

    @ManyToOne
    @JoinColumn(name = "projets_idprojet")
    private Projets projets;

    @OneToOne
    @JoinColumn(name = "methodepaiement_idpaiement")
    private Methodepaiement methodepaiement;
}
