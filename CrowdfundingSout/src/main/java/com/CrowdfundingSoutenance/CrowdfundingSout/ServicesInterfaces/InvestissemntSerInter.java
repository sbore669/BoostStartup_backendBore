package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investissements;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Startups;

import java.util.List;

public interface InvestissemntSerInter {


    List<Investissements> getAllInvestissement();
    List<Investissements> findAllByProjets(Projets projets);



}
