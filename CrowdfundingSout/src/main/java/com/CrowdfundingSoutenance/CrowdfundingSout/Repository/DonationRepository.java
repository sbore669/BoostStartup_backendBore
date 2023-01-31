package com.CrowdfundingSoutenance.CrowdfundingSout.Repository;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Action;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Donation;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByProjets(Projets projets);

    List<Donation> findByInvestisseur(Investisseur investisseur);
}
