package com.CrowdfundingSoutenance.CrowdfundingSout.Repository;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Startups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetsRepository extends JpaRepository<Projets, Long> {
    List<Projets> findByNomprojets(String NomProjets);

    List<Projets> findAllByStartups(Startups startups);

    List<Projets> findByStartups(Startups startups);

}
