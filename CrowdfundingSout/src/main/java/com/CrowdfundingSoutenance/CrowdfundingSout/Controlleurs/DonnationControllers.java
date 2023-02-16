package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Action;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Donation;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.DonationServInter;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.InvestisseurServInter;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.ProjetsInterfaces;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.Autres.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/don")
@CrossOrigin(origins = {"http://localhost:8100/", "http://localhost:4200/"}, maxAge = 3600,allowCredentials = "true")
public class DonnationControllers {


    @Autowired
    private InvestisseurServInter investisseurServInter;

    @Autowired
    private ProjetsInterfaces projetsInterfaces;


    @Autowired
    DonationServInter donationServInter;

    @PostMapping("/add/{idUsers}/{Idprojet}/")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> faireundon(@PathVariable Long idUsers, @PathVariable Long Idprojet,
                                               @RequestParam Long montantInvest){
        Investisseur investisseur = investisseurServInter.getInvestisseurById(idUsers);
        if (investisseur == null){
            return new ResponseEntity<>("Investisseur introuvable", HttpStatus.NOT_FOUND);
        }
        Projets projets = projetsInterfaces.getProjetById(Idprojet);
        if (projets == null){
            return new ResponseEntity<>("projets introuvable", HttpStatus.NOT_FOUND);
        }

        donationServInter.fairedon(projets,montantInvest,investisseur);
      //  return new ResponseEntity<>("Votre donnation a ete pris en compte avec Succès", HttpStatus.OK);
        return new ResponseEntity<Object>(new SuccessMessage("Votre donnation de " + montantInvest + " au projet " + projets.getNomprojets() + " ete pris en compte avec Succès"), HttpStatus.OK);
    }

    @GetMapping("/Projets/{Idprojet}")
    public ResponseEntity<List<Donation>> getInvestisseursByProjets(@PathVariable Long Idprojet){
        List<Investisseur> investisseurs = donationServInter.getInvestisseursByProjet(Idprojet);

        List<Donation> donations=donationServInter.getInvestissementByProjet(Idprojet);
        if (investisseurs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(donations, HttpStatus.OK);
    }

    @GetMapping("/invest/{IdUser}")
    public ResponseEntity<List<Donation>> getActionsByInvestisseur(@PathVariable Long IdUser){

        List<Donation> actionList = donationServInter.getDonationByInvestisseur(IdUser);

        //List<Action> actions=actionserviceInterf.getInvestissementByProjet(Idprojet);
        if (actionList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(actionList, HttpStatus.OK);
    }
}
