package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Action;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.ActionRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.ProjetsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.ActionserviceInterf;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ActionImplement implements ActionserviceInterf {

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    ProjetsRepository projetsRepository;
    @Override
    public String creeaction(Projets projets, Long nombreaction, Investisseur investisseur) {
        if (projets.getNbretotal_action() == 0  && projets.getPrix_action() == 0 ){
            return ("cet projets ne vend pas d'action");
        }
        if (nombreaction > projets.getAction_restante()){
            return "Le nombre d'action saisie est superieur au nombre restant";
        }
       /* if (nombreaction * projets.getPrix_action() != montantInvest){
         return "le montant de votre investissemnt ne correspond pas au nombre d'actions multiplié par le prix de l'action";
        }*/
        Action action = new Action();
        action.setProjets(projets);
        action.setDate_investissement(new Date());
        action.setMontantInvest(nombreaction * projets.getPrix_action());
        action.setNombreaction(nombreaction);
        action.setPrix_action(projets.getPrix_action());
        action.setTotal_action(projets.getNbretotal_action());
        action.setInvestisseur(investisseur);
        action.setAction_restante(projets.getAction_restante());
        investisseur.setTotalInvestissement(investisseur.getTotalInvestissement() + action.getMontantInvest());
        actionRepository.save(action);
        return "Vous avez acquis avec succes " + nombreaction + " action du projets " + projets.getNomprojets() + " avec succes ";
    }

    @Override
    public Action getActionById(Long id) {
        return actionRepository.findById(id).orElseThrow(null);
    }

    @Override
    public List<Action> getAllAction() {
        return actionRepository.findAll();
    }

  /*  @Override
    public List<Investisseur> getInvestisseursByProjet(Long idProjet) {
        return actionRepository.findInvestisseursByProjet(idProjet);
    }*/

}
