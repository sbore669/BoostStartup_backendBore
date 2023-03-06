package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Action;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Pret;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.InvestisseurReposotory;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.ProjetsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.InvestisseurServInter;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.PretInterfaceServ;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.ProjetsInterfaces;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.Autres.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/pret")
@CrossOrigin(origins = {"http://localhost:8100/", "http://localhost:4200/"}, maxAge = 3600,allowCredentials = "true")
public class PretController {

    @Autowired
    private PretInterfaceServ pretInterfaceServ;

    @Autowired
    private ProjetsRepository projetsRepository;


    @Autowired
    private InvestisseurServInter investisseurServInter;

    @Autowired
    private ProjetsInterfaces projetsInterfaces;

    @PostMapping("/add/{idUsers}/{Idprojet}/")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> createPret(@PathVariable Long idUsers,@PathVariable Long Idprojet,@RequestParam Long montantInvest) {
        Investisseur investisseur = investisseurServInter.getInvestisseurById(idUsers);
        if (investisseur == null){
            return new ResponseEntity<>("Investisseur introuvable", HttpStatus.NOT_FOUND);
        }
        Projets projets = projetsInterfaces.getProjetById(Idprojet);
        if (projets == null){
            return new ResponseEntity<>("projets introuvable", HttpStatus.NOT_FOUND);
        }
        if (projets.getPrettotalobtenu() >= projets.getObjectifpret()){
            return new ResponseEntity<>("Le projet a atteint son montant maximun de pret et n'est plus en messure d'accepter de cet pret", HttpStatus.NOT_FOUND);
        }
        if (projets.getPrettotalobtenu() == null){
            projets.setPrettotalobtenu(0L);
        }
        projets.setPrettotalobtenu(projets.getPrettotalobtenu() + montantInvest);
        pretInterfaceServ.faireunpret(projets, montantInvest, investisseur);
       // return new ResponseEntity<>("Votre pret a ete pris en compte avec Succès", HttpStatus.OK);
        return new ResponseEntity<Object>(new SuccessMessage("Votre pret de " + montantInvest + " au projets " + projets.getNomprojets() +" ete pris en compte avec Succès"  ), HttpStatus.OK);
    }

    @PutMapping("modf/{idPret}")
    public ResponseEntity<Pret> updatePretById(@PathVariable Long idPret,
                                               @RequestBody Pret pret) {
        Optional<Pret> pret1 = Optional.ofNullable(pretInterfaceServ.getPretById(idPret));
        if (pret1.isPresent()) {
            Pret _pret = pret1.get();

            _pret.setPourcentage(pret.getPourcentage());
            return new ResponseEntity<>(pretInterfaceServ.updatepret(_pret), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idPret}")
    public void deletePret(@PathVariable Long idPret) {
        pretInterfaceServ.deletePret(idPret);
    }

    @GetMapping("affpret/{idPret}")
    public Pret getPretById(@PathVariable Long idPret) {
        return pretInterfaceServ.getPretById(idPret);
    }

    @GetMapping("aff")
    public List<Pret> getAllPrets() {
        return pretInterfaceServ.getAllPrets();
    }

    @GetMapping("/Projets/{Idprojet}")
    public ResponseEntity<List<Pret>> getInvestisseursByProjets(@PathVariable Long Idprojet){
        List<Investisseur> investisseurs = pretInterfaceServ.getInvestisseursByProjet(Idprojet);

        List<Pret> pret=pretInterfaceServ.getInvestissementByProjet(Idprojet);
        if (investisseurs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pret, HttpStatus.OK);
    }

    @GetMapping("/invest/{IdUser}")
    public ResponseEntity<List<Pret>> getActionsByInvestisseur(@PathVariable Long IdUser){

        List<Pret> pretList = pretInterfaceServ.getPretByInvestisseur(IdUser);

        //List<Action> actions=actionserviceInterf.getInvestissementByProjet(Idprojet);
        if (pretList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pretList, HttpStatus.OK);
    }
}
