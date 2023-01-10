package com.CrowdfundingSoutenance.CrowdfundingSout.Repository;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Typeprojet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeProjetsRepository extends JpaRepository<Typeprojet, Long> {
    List<Typeprojet> findByNomtype(String Nomtype);

    boolean existsByNomtype(String Nomtype);
}
