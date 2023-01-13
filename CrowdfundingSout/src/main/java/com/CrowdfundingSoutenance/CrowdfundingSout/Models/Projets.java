package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Enum.StatProjets;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
public class Projets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idprojet;
    private String nomprojets;
    private String description;
    private Date dateLancement;
    private String objectif;
    private String dureeProjet;
    private Long budgetPrevisonnel;
    private String soldeProjet;
    private Long total_action;
    private Long action_restante;
    private Long prix_action;
    private Long pourcentage;
    private Long pret_minimun;
    private Long pret_maximun;
    private Long minimun_donation;
    private String image;

    @Enumerated(EnumType.STRING)
    private StatProjets statProjets;

    @ManyToOne
    @JoinColumn(name = "startup_id")
    private Startups startups;

    @ManyToOne
    @JoinColumn(name = "type_projet_idtypeprojets")
    private Typeprojet typeprojet;
}
