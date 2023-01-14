package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Donation extends Investissements{
    private Long donation_minimun;

    @ManyToOne
    @JoinColumn(name = "investisseur_id_users")
    private Investisseur investisseur;

}
