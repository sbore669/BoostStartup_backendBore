package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investissements;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.InvestissemntReposotory;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.InvestisseurReposotory;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.ProjetsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.InvestissemntSerInter;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.ProjetsInterfaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/investissements")
@CrossOrigin(origins = {"http://localhost:8100/", "http://localhost:4200/"}, maxAge = 3600,allowCredentials = "true")
public class InvestissementsControllers {

    @Autowired
    InvestissemntSerInter investissemntSerInter;

    @Autowired
    InvestissemntReposotory investissemntReposotory;

    @Autowired
    InvestisseurReposotory investisseurReposotory;

    @Autowired
    ProjetsInterfaces projetsInterfaces;

    @Autowired
    ProjetsRepository projetsRepository;



    @GetMapping("/afficher")
    public List<Investissements> findAll(){

        return investissemntSerInter.getAllInvestissement();
    }

    @GetMapping("/projets/{idprojets}")
    public List<Investissements> getInvestissementByProjets(@PathVariable Long idprojets){
        Projets projets = projetsRepository.findById(idprojets).orElseThrow(null);
        return investissemntReposotory.findAllByProjets(projets);
    }

}
