package com.CrowdfundingSoutenance.CrowdfundingSout.Repository;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Action;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investissements;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

   /* @Query("SELECT DISTINCT i FROM Action a JOIN a.investisseur i JOIN a.projets p WHERE p.idprojet = :idProjet")
    List<Investisseur> findInvestisseursByProjet(@Param("idProjet") Long idProjet);*/

    List<Action> findByProjets(Projets projets);

    List<Action> findByInvestisseur(Investisseur investisseur);
}
