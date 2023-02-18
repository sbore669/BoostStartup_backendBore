package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Action;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.ActionserviceInterf;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.InvestisseurServInter;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.ProjetsInterfaces;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.Autres.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/action")
@CrossOrigin(origins = {"http://localhost:8100/", "http://localhost:8200/"}, maxAge = 3600,allowCredentials = "true")
public class ActionControllers {
    @Autowired
    InvestisseurServInter investisseurServInter;
    @Autowired
    ProjetsInterfaces projetsInterfaces;

    @Autowired
    ActionserviceInterf actionserviceInterf;


    @PostMapping("/add/{idUsers}/{Idprojet}")
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> creeaction(@PathVariable(value = "idUsers") Long idUsers,
                             @PathVariable(value = "Idprojet") Long Idprojet,
                             @RequestParam(value = "nbreaction") String nbreaction)
    {
        Long nombreaction= Long.valueOf(nbreaction);
        Investisseur investisseur = investisseurServInter.getInvestisseurById(idUsers);
        if (investisseur == null){
            return new ResponseEntity<>("Investisseur introuvable",HttpStatus.NOT_FOUND);
        }
        Projets projets = projetsInterfaces.getProjetById(Idprojet);
        if (projets == null) {
            return new ResponseEntity<>("Aucun projet trouvé avec l'ID spécifié",HttpStatus.NOT_FOUND);
        }
        if (projets.getNbretotal_action() == 0 && projets.getPrix_action() == 0){
            return new ResponseEntity<>("cet projets ne vend pas d'action",HttpStatus.BAD_REQUEST);
        }
        if (nombreaction > projets.getAction_restante()){
            return new ResponseEntity<>("Le nombre d'action saisie est superieur au nombre disponible",HttpStatus.BAD_REQUEST);
        }

        //projets.setAction_restante(projets.getAction_restante() - nombreaction);

        Action action = new Action();

        action.setMontantInvest(nombreaction * projets.getPrix_action());

        action.setAction_restante(projets.getAction_restante());

        projets.setActiontotalVendu(projets.getActiontotalVendu() + action.getMontantInvest());

        actionserviceInterf.creeaction(projets,nombreaction,investisseur);

        return new ResponseEntity<Object>(new SuccessMessage("Vous avez acquis avec succès " + nombreaction + " action(s) du projet " + projets.getNomprojets() + " avec succès"), HttpStatus.OK);


    }

    @GetMapping("/Projets/{Idprojet}")
    public ResponseEntity<List<Action>> getInvestisseursByProjets(@PathVariable Long Idprojet){
        List<Investisseur> investisseurs = actionserviceInterf.getInvestisseursByProjet(Idprojet);

        List<Action> actions=actionserviceInterf.getInvestissementByProjet(Idprojet);
        if (investisseurs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(actions, HttpStatus.OK);
    }

    @GetMapping("/invest/{IdUser}")
    public ResponseEntity<List<Action>> getActionsByInvestisseur(@PathVariable Long IdUser){

        List<Action> actionList = actionserviceInterf.getActionsByInvestisseur(IdUser);

        //List<Action> actions=actionserviceInterf.getInvestissementByProjet(Idprojet);
        if (actionList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(actionList, HttpStatus.OK);
    }
}
