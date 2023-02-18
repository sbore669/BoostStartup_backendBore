package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Startups;

import java.util.List;

public interface ProjetsInterfaces {
    Projets addProjet(Projets projets);
    Projets updateProjet(long idprojets,Projets projets);
    void delete(Long idprojets);
    Projets getProjetById(Long idprojets);
    List<Projets> getAllProjet();
    List<Projets> findByNomProjets(String nomProjet, Long id_users);
    List<Projets> findAllByStartups(Startups startups);
    Long getTotalObtenuByStartupId(Long id_users);
    Long getTotalDonationByStartupId(Long id_users);
    Long getTotalPretByStartupId(Long id_users);
    Long countProjetsByStartupId(Long id_users);
    Long getTotalObtenuForAllStartups();
    Long getTotalDonationForAllStartups();
    Long getTotalPretForAllStartups();
    Long countAllProjets();
}
