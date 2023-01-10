package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.ProjetsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.ProjetsInterfaces;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class ProjetsServiceImplm implements ProjetsInterfaces {

    @Autowired
    private ProjetsRepository projetsRepository;


    @Override
    public Projets addProjet(Projets projets) {
        if (projets.getNomprojets() == null || projets.getNomprojets().isEmpty()) {
            throw new IllegalArgumentException("Le nom du projet est requis");
        }

        if (projets.getBudgetPrevisonnel() <= 0) {
            throw new IllegalArgumentException("Le budget prévisionnel doit être supérieur à 0");
        }

        List<Projets> existingProjects = projetsRepository.findByNomprojets(projets.getNomprojets());
        if (!existingProjects.isEmpty()) {
            throw new IllegalArgumentException("Un projet existe déjà avec ce nom");
        }



        // Vérifiez les autres champs requis
        return projetsRepository.save(projets);
    }

    @Override
    public Projets updateProjet(long id, Projets projet) {
        Projets projetToUpdate = projetsRepository.findById(id).orElse(null);
        if (projetToUpdate == null) {
            throw new IllegalArgumentException("Aucun projet trouvé avec l'ID spécifié");
        }

        // Vérifiez que les champs à mettre à jour sont valides
        if (projet.getNomprojets() != null && !projet.getNomprojets().isEmpty()) {
            projetToUpdate.setNomprojets(projet.getNomprojets());
        }
        if (projet.getDescription() != null && !projet.getDescription().isEmpty()) {
            projetToUpdate.setDescription(projet.getDescription());
        }
        // Vérifiez les autres champs à mettre à jour

        return projetsRepository.save(projetToUpdate);
    }



    @Override
    public void delete(Long idprojets) {
    projetsRepository.deleteById(idprojets);
    }

    @Override
    public Projets getProjetById(Long idprojets) {
        return projetsRepository.findById(idprojets).orElse(null);
    }

    @Override
    public List<Projets> findAll() {
        return projetsRepository.findAll();
    }

    @Override
    public List<Projets> findByNomProjets(String NomProjets, Long id_users) {
        return projetsRepository.findByNomprojets(NomProjets);
    }

}
