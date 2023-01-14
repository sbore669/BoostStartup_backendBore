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
public class Action extends Investissements {

    private Long nombreaction;
    private Long prix_action;
    private Long total_action;
    private Long action_restante;


    @ManyToOne
    @JoinColumn(name = "investisseur_id_users")
    private Investisseur investisseur;
}

