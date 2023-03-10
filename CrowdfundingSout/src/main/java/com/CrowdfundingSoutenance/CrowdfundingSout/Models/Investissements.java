package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Investissements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idinvest;
    private Long montantInvest;
    private Date date_investissement;

    @ManyToOne
    @JoinColumn(name = "projets_idprojet")
    private Projets projets;

    @OneToOne
    @JoinColumn(name = "methodepaiement_idpaiement")
    private Methodepaiement methodepaiement;
}
