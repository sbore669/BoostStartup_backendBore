package com.CrowdfundingSoutenance.CrowdfundingSout.Repository;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Startups;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StartupsRepository extends JpaRepository<Startups, Long> {
    List<Startups> findByNomStartups(String name);

    Startups findByEmail(String email);


}
