package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.*;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Enum.StatProjets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Enum.Status;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.ProjetsInterfaces;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.StartupsInterfaces;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.Autres.ConfigImages;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.response.MessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/projets")
public class ProjetControllers {

    @Autowired
    private ProjetsInterfaces projetsInterfaces;

    @Autowired
    private StartupsInterfaces startupsInterfaces;

    @PostMapping("/add/{id_users}")
    @PreAuthorize("hasRole('ROLE_STARTUPS')")
    public ResponseEntity<?> addProjet(
            @PathVariable Long id_users,
            @Valid @RequestParam(value = "file", required = true) MultipartFile file,
            @Valid @RequestParam(value = "donneprojet")String donneprojet) throws IOException {
        Startups startup = startupsInterfaces.getStartupsById(id_users);
        if (startup == null) {
            return new ResponseEntity<>("Entreprise introuvable", HttpStatus.NOT_FOUND);
        }

        ObjectMapper mapper = new ObjectMapper();
        Projets projet = mapper.readValue(donneprojet, Projets.class);
        List<Projets> existingProjects = projetsInterfaces.findByNomProjets(projet.getNomprojets(), id_users);
        if (!existingProjects.isEmpty()) {
            return new ResponseEntity<>("Un projet existe déjà avec ce nom pour cette entreprise", HttpStatus.BAD_REQUEST);
        }

        if (projet.getNomprojets() == null || projet.getNomprojets().isEmpty()) {
            return new ResponseEntity<>("Le nom du projet est requis", HttpStatus.BAD_REQUEST);
        }
        if (projet.getBudgetPrevisonnel() <= 0) {
            return new ResponseEntity<>("Le budget prévisionnel doit être supérieur à 0", HttpStatus.BAD_REQUEST);
        }
        if (projet.getStatProjets() == null){
            projet.setStatProjets(StatProjets.ENCOURS);
        } else if (Arrays.asList(Status.values()).contains(projet.getStatProjets())) {
            throw new IllegalArgumentException("L'état du projets de votren startups est incorrect");
        }

        String url = "C:/Users/sbbore/Pictures/springimages";
        String nomfile = StringUtils.cleanPath(file.getOriginalFilename());
        ConfigImages.saveimg(url, nomfile, file);
        projet.setStartups(startup);
        projet.setImage(nomfile);
        projet.setDateLancement(new Date());
        projet = projetsInterfaces.addProjet(projet);

        return new ResponseEntity<>(new MessageResponse("Projet créé avec succès"), HttpStatus.CREATED);
    }
    @PutMapping("/update/{idprojet}")
    public ResponseEntity<?> updateProjet(
            @PathVariable Long idprojet,
            @Valid @RequestParam(value = "file", required = false) MultipartFile file,
            @Valid @RequestParam(value = "donneprojet") String donneprojet) throws IOException {

        Projets projetToUpdate = projetsInterfaces.getProjetById(idprojet);
        if (projetToUpdate == null) {
            return new ResponseEntity<>("Aucun projet trouvé avec l'ID spécifié", HttpStatus.NOT_FOUND);
        }

        ObjectMapper mapper = new ObjectMapper();
        Projets projet = mapper.readValue(donneprojet, Projets.class);
        if (projet.getNomprojets() != null && !projet.getNomprojets().isEmpty()) {
            projetToUpdate.setNomprojets(projet.getNomprojets());
        }
        if (projet.getDescription() != null && !projet.getDescription().isEmpty()) {
            projetToUpdate.setDescription(projet.getDescription());
        }

        if (file != null) {
            String url = "C:/Users/sbbore/Pictures/springimages";
            String nomfile = StringUtils.cleanPath(file.getOriginalFilename());
            ConfigImages.saveimg(url, nomfile, file);
            projetToUpdate.setImage(nomfile);
        }

        projetToUpdate = projetsInterfaces.updateProjet(idprojet, projetToUpdate);
        return new ResponseEntity<>(new MessageResponse("Projet mis à jour avec succès"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idprojet}")
    public ResponseEntity<?> deleteProjet(@PathVariable Long idprojet) {
        Projets projetToDelete = projetsInterfaces.getProjetById(idprojet);
        if (projetToDelete == null) {
            return new ResponseEntity<>("Aucun projet trouvé avec l'ID spécifié", HttpStatus.NOT_FOUND);
        }

        projetsInterfaces.delete(idprojet);
        return new ResponseEntity<>(new MessageResponse("Projet supprimé avec succès"), HttpStatus.OK);
    }




}




