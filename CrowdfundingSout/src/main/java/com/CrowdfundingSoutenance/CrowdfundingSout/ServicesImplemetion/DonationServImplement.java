package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.*;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.DonationRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.InvestisseurReposotory;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.ProjetsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.DonationServInter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class DonationServImplement implements DonationServInter {

    @Autowired
    DonationRepository donationRepository;
    @Autowired
    ProjetsRepository projetsRepository;

    @Autowired
    InvestisseurReposotory investisseurReposotory;
    @Override
    public Donation fairedon(Projets projets, Long montantInvest, Investisseur investisseur) {
        if(montantInvest <= projets.getMinimun_donation()){
            throw new IllegalArgumentException("Desoler le montant minimun de donation accepter pour cet projet est de " +projets.getMinimun_donation());
        }

        Donation donation = new Donation();
        donation.setProjets(projets);
        donation.setDate_investissement(new Date());
        donation.setMontantInvest(montantInvest);
        donation.setDonation_minimun(projets.getMinimun_donation());
        donation.setInvestisseur(investisseur);
        projets.setDonationtotalobtenu(projets.getDonationtotalobtenu() + montantInvest);
        investisseur.setTotalInvestissement(investisseur.getTotalInvestissement() + donation.getMontantInvest());
        return donationRepository.save(donation);
    }

    @Override
    public List<Donation> getAllDonation() {

        return donationRepository.findAll();
    }
    @Override
    public Donation getDonationById(Long id) {

        return donationRepository.findById(id).get();
    }
    @Override
    public List<Donation> getInvestissementByProjet(Long idProjet) {
        Projets p=projetsRepository.findById(idProjet).get();
        return donationRepository.findByProjets(p);
    }
    @Override
    public List<Donation> getDonationByInvestisseur(Long idUser) {
        Investisseur investisseur=investisseurReposotory.findById(idUser).get();
        return donationRepository.findByInvestisseur(investisseur);
    }
    @Override
    public List<Investisseur> getInvestisseursByProjet(Long idProjet) {
        Projets p=projetsRepository.findById(idProjet).get();

        List<Donation> list=donationRepository.findByProjets(p);

        List<Investisseur> investisseurList=new ArrayList<>();

        for (Donation d :
                list) {
            if(!investisseurList.contains(d.getInvestisseur())){
                investisseurList.add(d.getInvestisseur());
            }
        }
        return investisseurList;
    }


}
