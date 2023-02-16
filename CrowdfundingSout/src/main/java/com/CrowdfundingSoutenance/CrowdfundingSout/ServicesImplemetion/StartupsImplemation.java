package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Enum.Status;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Startups;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.StartupsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.StartupsInterfaces;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.Autres.EmailConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StartupsImplemation implements StartupsInterfaces {

    @Autowired
    private StartupsRepository startupsRepository;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    EmailConstructor emailConstructor;
    @Autowired
    PasswordEncoder encoder;


    @Override
    public Startups createStartups(Startups startups) {
      //  mailSender.send(emailConstructor.constructNewUserEmail(startups));
        return startupsRepository.save(startups);
    }

  /*@Override
    public String updateStartupsById(Long id, Startups startup) {
    Startups existingStartup = startupsRepository.findById(id).orElse(null);
    if (existingStartup != null) {
       // update the fields of the existing startup with the new values
            existingStartup.setNomStartups(startup.getNomStartups());
            existingStartup.setContact(startup.getContact());
            existingStartup.setEmailStartups(startup.getEmailStartups());
            existingStartup.setSecteurActivite(startup.getSecteurActivite());
            existingStartup.setStadeDeveloppement(startup.getStadeDeveloppement());
            existingStartup.setNumeroIdentification(startup.getNumeroIdentification());
            existingStartup.setDescriptionStartups(startup.getDescriptionStartups());
            existingStartup.setDateCreation(startup.getDateCreation());
            existingStartup.setProprietaire(startup.getProprietaire());
            existingStartup.setFormeJuridique(startup.getFormeJuridique());
            existingStartup.setChiffreAffaire(startup.getChiffreAffaire());
            existingStartup.setLocalisation(startup.getLocalisation());
            existingStartup.setPays(startup.getPays());
            existingStartup.setStatus(startup.getStatus());

            //update the related Utilisateurs entity
            existingStartup.setUsername(startup.getUsername());
            existingStartup.setEmail(startup.getEmail());
           // existingStartup.setPassword(startup.getPassword());
            existingStartup.setNomcomplet(startup.getNomcomplet());
            existingStartup.setPhoto(startup.getPhoto());
            existingStartup.setAdresse(startup.getAdresse());
            startupsRepository.save(existingStartup);
            return "startups modifier avec succes";
        } else {
            return "echec lors de la modification";
        }
    }*/
  @Override
  public String updateStartupsById(Long id, Startups startup) {
      Startups existingStartup = startupsRepository.findById(id).orElse(null);
      if (existingStartup != null) {
          BeanUtils.copyProperties(startup, existingStartup, getNullPropertyNames(startup));
          startupsRepository.save(existingStartup);
          return "startup modifiée avec succès";
      } else {
          return "échec lors de la modification";
      }
  }
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
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
    public Long getTotalStartup() {
        List<Startups> startups = startupsRepository.findAll();
        return (long) startups.size();
    }

    @Override
    public List<Startups> getStartupsByName(String name) {
        return startupsRepository.findByNomStartups(name);
    }

    @Override
    public List<Startups> findByStatus(Status status) {
        return startupsRepository.findByStatus(status);
    }


}
