package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investissements;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Startups;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.InvestissemntReposotory;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.ProjetsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.StartupsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.TypeProjetsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.NotificationServInter;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.ProjetsInterfaces;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

    @Autowired
    private TypeProjetsRepository typeProjetsRepository;

    @Autowired
    private StartupsRepository startupsRepository;


    @Autowired
    private NotificationServInter notificationServInter;
    @Autowired
    private InvestissemntReposotory investissemntReposotory;






    @Override
    public Projets addProjet(Projets projets) {
        if (projets.getNomprojets() == null || projets.getNomprojets().isEmpty()) {
            throw new IllegalArgumentException("Le nom du projet est requis");
        }
        if (projets.getBudgetPrevisonnel() <= 0) {
            throw new IllegalArgumentException("Le budget prévisionnel doit être supérieur à 0 ou le pourcentage du pret superieur a 100%");
        }
        if (projets.getObjectifpret()<= projets.getPret_maximun()){
            throw new IllegalArgumentException("Le pret maximun ne peut etre superieur a l'objectif du pret");
        }
        if (projets.getObjectifpret()<0){
            throw new IllegalArgumentException("La valeur de l'objectif du pret ne peut etre inferieur a 0");
        }
        if (projets.getPret_minimun() >= projets.getPret_maximun()) {
            throw new IllegalArgumentException("La valeur minimale du prêt est supérieure à la valeur maximale du prêt." + "Veuillez saisir des valeurs correctes.");
        }
        if (projets.getMinimun_donation()<= 0){
            throw new IllegalArgumentException("Le minimun de donation doit être superieur a 0");
        }
        if (projets.getPourcentage() < 0){
            throw new IllegalArgumentException("Le pourcentage du prêt saisi ne peut etre negatif.");
        }
        if (projets.getPourcentage() >=100){
            throw new IllegalArgumentException("Le pourcentage du prêt saisi doit être compris entre 0 et 100%.");
        }
        if (projets.getDonationtotalobtenu() == null){
            projets.setDonationtotalobtenu(0L);
        }
        if (projets.getSoldeprojet() == null){
            projets.setSoldeprojet(0L);
        }
        if (projets.getPrettotalobtenu() == null){
            projets.setPrettotalobtenu(0L);
        }
        if (projets.getActiontotalVendu() == null){
            projets.setActiontotalVendu(0L);
        }
        if (projets.getAction_restante() == null){
            projets.setAction_restante(projets.getNbretotal_action());
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
    public List<Projets> getAllProjet() {

        return projetsRepository.findAll();
    }

    @Override
    public List<Projets> findByNomProjets(String NomProjets, Long id_users) {
        return projetsRepository.findByNomprojets(NomProjets);
    }

    @Override
    public List<Projets> findAllByStartups(Startups startups) {
        return projetsRepository.findAllByStartups(startups);
    }

    @Override
    public Long getTotalObtenuByStartupId(Long id_users) {
        Startups startups = startupsRepository.findById(id_users).get();
        List<Projets> projets = projetsRepository.findByStartups(startups);
        return projets.stream().mapToLong(Projets::getSoldeprojet).sum();
    }
    @Override
    public Long getTotalDonationByStartupId(Long id_users) {
        Startups startups = startupsRepository.findById(id_users).get();
        List<Projets> projets = projetsRepository.findByStartups(startups);
        return projets.stream().mapToLong(Projets::getDonationtotalobtenu).sum();
    }

    @Override
    public Long getTotalPretByStartupId(Long id_users) {
        Startups startups = startupsRepository.findById(id_users).get();
        List<Projets> projets = projetsRepository.findByStartups(startups);
        return projets.stream().mapToLong(Projets::getPrettotalobtenu).sum();
    }

    public Long countProjetsByStartupId(Long id_users) {
        Startups startups = startupsRepository.findById(id_users).get();
        List<Projets> projets = projetsRepository.findByStartups(startups);
        return (long) projets.size();
    }
    //Calculer le total obtenus reçus
   @Override
   public Long getTotalObtenuForAllStartups() {
       List<Investissements> investissements = investissemntReposotory.findAll();
       Long total = 0L;
       for (Investissements investissements1 : investissements) {
           System.out.println(total);
           total += investissements1.getMontantInvest();
       }
       return total;
   }


   //Calculer le total des donnation obtenu sur la plateforme
   @Override
   public Long getTotalDonationForAllStartups() {
       List<Startups> startups = startupsRepository.findAll();
       Long totaldon = 0L;
       for (Startups startup : startups) {
           totaldon += getTotalDonationByStartupId(startup.getId());
       }
       return totaldon;
   }

   //Calculer le total des pret sur l'ensemble des projets
   @Override
   public Long getTotalPretForAllStartups() {
       List<Startups> startups = startupsRepository.findAll();
       Long totalpret = 0L;
       for (Startups startup : startups) {
           totalpret += getTotalPretByStartupId(startup.getId());
       }
       return totalpret;
   }

    @Override
    public Long countAllProjets() {
        List<Projets> projets = projetsRepository.findAll();
        return (long) projets.size();
    }


}
