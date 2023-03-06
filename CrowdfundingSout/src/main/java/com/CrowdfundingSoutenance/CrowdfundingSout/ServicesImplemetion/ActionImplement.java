package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Action;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investissements;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.ActionRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.InvestisseurReposotory;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.ProjetsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.ActionserviceInterf;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ActionImplement implements ActionserviceInterf {

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    ProjetsRepository projetsRepository;

    @Autowired
    InvestisseurReposotory investisseurReposotory;
    @Override
    public ResponseEntity<?> creeaction(Projets projets, Long nombreaction, Investisseur investisseur) {
        if (projets.getNbretotal_action() == 0 && projets.getPrix_action() == 0 ){
            return new ResponseEntity<>("cet projets ne vend pas d'action", HttpStatus.BAD_REQUEST);
        }
        if (nombreaction > projets.getAction_restante()){
            return new ResponseEntity<>("Le nombre d'action saisie est supérieur au nombre restant", HttpStatus.BAD_REQUEST);
        }

        projets.setAction_restante(projets.getAction_restante() - nombreaction);

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
        return new ResponseEntity<>("Vous avez acquis avec succès " + nombreaction + " action du projet " + projets.getNomprojets(), HttpStatus.OK);
    }
   /* @Override
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
     /*   Action action = new Action();
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
    }*/

    @Override
    public Action getActionById(Long id) {
        return actionRepository.findById(id).orElseThrow(null);
    }

    @Override
    public List<Action> getAllAction() {
        return actionRepository.findAll();
    }

    @Override
    public List<Investisseur> getInvestisseursByProjet(Long idProjet) {
        Projets p=projetsRepository.findById(idProjet).get();

        List<Action> list=actionRepository.findByProjets(p);

        List<Investisseur> investisseurList=new ArrayList<>();

        for (Action a :
                list) {
            if(!investisseurList.contains(a.getInvestisseur())){
                investisseurList.add(a.getInvestisseur());
            }
        }
        return investisseurList;
    }

    @Override
    public List<Action> getInvestissementByProjet(Long idProjet) {
        Projets p=projetsRepository.findById(idProjet).get();

        return actionRepository.findByProjets(p);
    }

    @Override
    public List<Action> getActionsByInvestisseur(Long idUser) {
        Investisseur investisseur=investisseurReposotory.findById(idUser).get();
        return actionRepository.findByInvestisseur(investisseur);
    }

  /*  @Override
    public List<Investisseur> getInvestisseursByProjet(Long idProjet) {
        return actionRepository.findInvestisseursByProjet(idProjet);
    }*/

}
