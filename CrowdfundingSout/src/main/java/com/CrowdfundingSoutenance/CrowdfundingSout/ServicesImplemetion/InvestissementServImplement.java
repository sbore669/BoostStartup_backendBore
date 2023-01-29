package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion;


import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investissements;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Startups;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.InvestissemntReposotory;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.InvestissemntSerInter;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.InvestisseurServInter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InvestissementServImplement implements InvestissemntSerInter {

    @Autowired
    InvestissemntReposotory investissemntReposotory;


    @Override
    public List<Investissements> getAllInvestissement() {
        return investissemntReposotory.findAll();
    }

    @Override
    public List<Investissements> findAllByProjets(Projets projets) {
        return investissemntReposotory.findAllByProjets(projets);
    }


}
