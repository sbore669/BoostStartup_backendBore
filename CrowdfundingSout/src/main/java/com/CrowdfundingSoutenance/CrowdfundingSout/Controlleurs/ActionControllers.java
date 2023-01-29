package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Action;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.ActionserviceInterf;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.InvestisseurServInter;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.ProjetsInterfaces;
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
    public String creeaction(@PathVariable(value = "idUsers") Long idUsers,
                             @PathVariable(value = "Idprojet") Long Idprojet,
                             @RequestParam(value = "nbreaction") String nbreaction)
    {
        Long nombreaction= Long.valueOf(nbreaction);

        Investisseur investisseur = investisseurServInter.getInvestisseurById(idUsers);
        if (investisseur == null){
            return "Investisseur introuvable";
        }
        Projets projets = projetsInterfaces.getProjetById(Idprojet);
        if (projets == null) {
            return "Aucun projet trouvé avec l'ID spécifié";
        }
        if (projets.getNbretotal_action() == 0 && projets.getPrix_action() == 0){
            return "cet projets ne vend pas d'action";
        }
        if (nombreaction > projets.getAction_restante()){
            return "Le nombre d'action saisie est superieur au nombre disponible";
        }

        projets.setAction_restante(projets.getAction_restante() - nombreaction);
        Action action = new Action();
        action.setMontantInvest(nombreaction * projets.getPrix_action());
        action.setAction_restante(projets.getAction_restante());

        actionserviceInterf.creeaction(projets,nombreaction,investisseur);
        return "Vous avez acquis avec succes " + nombreaction + "action du projets " + projets.getNomprojets() + " avec succes";
    }

  /*  @GetMapping("/projet/{idProjet}")
    public ResponseEntity<List<Investisseur>> getInvestisseursByProjet(@PathVariable Long idProjet) {
        List<Investisseur> investisseurs = actionserviceInterf.getInvestisseursByProjet(idProjet);
        if (investisseurs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(investisseurs, HttpStatus.OK);
    }*/
}
