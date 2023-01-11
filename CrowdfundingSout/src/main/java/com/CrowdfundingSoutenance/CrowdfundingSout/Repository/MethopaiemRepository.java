package com.CrowdfundingSoutenance.CrowdfundingSout.Repository;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Methodepaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethopaiemRepository extends JpaRepository<Methodepaiement, Long> {
    Methodepaiement findByNompaiement(String nompaiement);
}
