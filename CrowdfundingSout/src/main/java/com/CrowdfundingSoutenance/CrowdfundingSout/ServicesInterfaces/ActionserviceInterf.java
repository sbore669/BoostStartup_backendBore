package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Action;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;

import java.util.List;

public interface ActionserviceInterf {

    String creeaction(Projets projets, Long nombreaction, Investisseur investisseur);
    Action getActionById(Long id);
    List<Action> getAllAction();
}
