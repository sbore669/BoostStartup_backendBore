package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Donation;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.DonationRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.DonationServInter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class DonationServImplement implements DonationServInter {

    @Autowired
    DonationRepository donationRepository;
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
        return donationRepository.save(donation);
    }

    @Override
    public List<Donation> getAllDonation() {
        return null;
    }

    @Override
    public Donation getDonationById(Long id) {
        return null;
    }

    @Override
    public Donation getDonationByProjets(Long idprojet) {
        return null;
    }
}
