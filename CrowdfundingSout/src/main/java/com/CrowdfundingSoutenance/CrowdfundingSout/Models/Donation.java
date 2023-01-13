package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Donation extends Investissements{
    private Long donation_minimun;

    @ManyToOne
    @JoinColumn(name = "investisseur_id_users")
    private Investisseur investisseur;

}
