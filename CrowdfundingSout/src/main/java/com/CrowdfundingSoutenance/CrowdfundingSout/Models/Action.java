package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@Data
public class Action extends Investissements {

    private Long nombre_action;
    private Long nombre_acquis;
    private Long total_action;
    private Long action_restante;
    private Long prix_action;

    @ManyToOne
    @JoinColumn(name = "investisseur_id_users")
    private Investisseur investisseur;
}

