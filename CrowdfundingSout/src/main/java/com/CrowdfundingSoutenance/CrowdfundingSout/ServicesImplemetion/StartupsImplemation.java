package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Startups;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.StartupsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.StartupsInterfaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StartupsImplemation implements StartupsInterfaces {

    @Autowired
    private StartupsRepository startupsRepository;

    @Override
    public Startups createStartups(Startups startups) {
        return startupsRepository.save(startups);
    }

    @Override
    public String updateStartupsById(Long id, Startups startups) {
        Startups startupToUpdate = startupsRepository.findById(id).orElse(null);
        if (startupToUpdate != null) {
            startupToUpdate.setNomStartups(startups.getNomStartups());
            startupToUpdate.setContact(startups.getContact());
            startupToUpdate.setEmailStartups(startups.getEmailStartups());
            startupToUpdate.setSecteurActivite(startups.getSecteurActivite());
            startupToUpdate.setStadeDeveloppement(startups.getStadeDeveloppement());
            startupToUpdate.setNumeroIdentification(startups.getNumeroIdentification());
            startupToUpdate.setDescriptionStartups(startups.getDescriptionStartups());
            startupToUpdate.setDateCreation(startups.getDateCreation());
            startupToUpdate.setProprietaire(startups.getProprietaire());
            startupToUpdate.setFormeJuridique(startups.getFormeJuridique());
            startupToUpdate.setChiffreAffaire(startups.getChiffreAffaire());
            startupToUpdate.setLocalisation(startups.getLocalisation());
            startupToUpdate.setPays(startups.getPays());
            startupToUpdate.setStatus(startups.getStatus());
            startupsRepository.save(startupToUpdate);
            return "Startups modifier avec succès";
        } else {
            return "Votre Startups est introuvable";
        }
    }

    @Override
    public void deleteStartups(Long id) {
        startupsRepository.deleteById(id);
    }

    @Override
    public Startups getStartupsById(Long id) {
        return startupsRepository.findById(id).orElse(null);
    }

    @Override
    public List<Startups> getAllStartups() {
        return (List<Startups>) startupsRepository.findAll();
    }

    @Override
    public List<Startups> getStartupsByName(String name) {
        return startupsRepository.findByNomStartups(name);
    }
}
