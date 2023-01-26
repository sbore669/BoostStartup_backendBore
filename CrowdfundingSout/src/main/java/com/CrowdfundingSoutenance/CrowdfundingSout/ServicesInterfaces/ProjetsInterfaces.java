package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;

import java.util.List;

public interface ProjetsInterfaces {
    Projets addProjet(Projets projets);
    Projets updateProjet(long idprojets,Projets projets);
    void delete(Long idprojets);
    Projets getProjetById(Long idprojets);
    List<Projets> getAllProjet();
    List<Projets> findByNomProjets(String nomProjet, Long id_users);
}
