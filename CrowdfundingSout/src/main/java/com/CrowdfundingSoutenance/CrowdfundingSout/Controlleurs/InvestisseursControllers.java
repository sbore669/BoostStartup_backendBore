package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Typeprojet;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.InvestisseurReposotory;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.TypeProjetsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.InvestisseurServInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/invest")
public class InvestisseursControllers {

    @Autowired
    private InvestisseurServInter investisseurServInter;
    @Autowired
    private InvestisseurReposotory investisseurReposotory;

    @Autowired
    private TypeProjetsRepository typeProjetsRepository;



    @GetMapping("/afficher")
    public List<Investisseur> getAllInvestisseur(){
        return investisseurServInter.getAllInvestisseur();
    }


    @GetMapping("/typeprojet/{Idtypeprojets}")
    public ResponseEntity<List<Investisseur>> getInvestisseursByTypeProjet(@PathVariable Long Idtypeprojets) {
        Typeprojet typeprojet = typeProjetsRepository.findById(Idtypeprojets).orElse(null);
        if (typeprojet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Investisseur> investisseurs = investisseurServInter.getInvestisseursByTypeProjet(typeprojet);
        return new ResponseEntity<>(investisseurs, HttpStatus.OK);
    }

    @GetMapping("/{idUsers}")
    public ResponseEntity<Investisseur> getInvestisseurById(@PathVariable Long idUsers) {
        Investisseur investisseur = investisseurServInter.getInvestisseurById(idUsers);
        if (investisseur == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(investisseur, HttpStatus.OK);
    }

}
