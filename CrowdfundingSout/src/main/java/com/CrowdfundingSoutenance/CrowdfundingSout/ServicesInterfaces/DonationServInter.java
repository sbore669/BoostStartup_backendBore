package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Action;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Donation;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;

import java.util.List;

public interface DonationServInter {
    Donation fairedon(Projets projets, Long montantInvest, Investisseur investisseur);
    List<Donation> getAllDonation();
    Donation getDonationById(Long id);
  //  Donation getDonationByProjets(Long idprojet);
    List<Donation> getInvestissementByProjet(Long idProjet);
    List<Investisseur> getInvestisseursByProjet(Long idProjet);

    List<Donation> getDonationByInvestisseur(Long idUser);
}
