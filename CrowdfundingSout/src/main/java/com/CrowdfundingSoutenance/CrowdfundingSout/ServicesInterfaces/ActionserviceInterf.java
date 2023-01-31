package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Action;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ActionserviceInterf {

    ResponseEntity<?> creeaction(Projets projets, Long nombreaction, Investisseur investisseur);
    Action getActionById(Long id);
    List<Action> getAllAction();

    List<Investisseur> getInvestisseursByProjet(Long idProjet);

    List<Action> getInvestissementByProjet(Long idProjet);

    List<Action> getActionsByInvestisseur(Long idUser);

}
