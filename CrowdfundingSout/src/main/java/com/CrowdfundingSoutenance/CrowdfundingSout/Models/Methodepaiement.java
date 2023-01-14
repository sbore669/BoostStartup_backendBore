package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Methodepaiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpaiement;
    private String nompaiement;
    private String adressepaiement;

}
