package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Typeprojet;

import java.util.List;

public interface InvestisseurServInter {
    Investisseur createInvest(Investisseur investisseur);
    Investisseur updateById(Long idUsers, Investisseur investisseur);
    List<Investisseur> getAllInvestisseur();
    Investisseur getInvestisseurById(Long idUsers);

    List<Investisseur> getInvestisseursByTypeProjet(Typeprojet typeprojet);
    Long investisseurTotal();
}
