package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Enum.Status;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Startups;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Typeprojet;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.StartupsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.StartupsInterfaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/startup")
@CrossOrigin(origins = {"http://localhost:8100/", "http://localhost:4200/"}, maxAge = 3600,allowCredentials = "true")
public class StartupControllers {

    @Autowired
    StartupsInterfaces startupsInterfaces;

    @Autowired
    StartupsRepository startupsRepository;

    @GetMapping("/status/VALIDER")
    public List<Startups> getStartupsByStatus() {
        return startupsInterfaces.findByStatus(Status.VALIDER);
    }

    @GetMapping("/start/{idUsers}")
    public ResponseEntity<?> getStartupsById(@PathVariable Long idUsers){
        Startups startups = startupsInterfaces.getStartupsById(idUsers);
        if (startups == null){
            return new ResponseEntity<>("Startups introuvable", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(startups,HttpStatus.OK);
    }



    @GetMapping("/affall")
    public List<Startups> getAllStartups() {
        return startupsInterfaces.getAllStartups();
    }
  /*  @GetMapping("/totauxdon")
    public ResponseEntity<Long> getTotalSonationForAllStartups(){
        Long total = projetsInterfaces.getTotalObtenuForAllStartups();
        return ResponseEntity.ok(total);
    }*/
    @GetMapping("/totaux")
    public ResponseEntity<Long> getTotalStartup(){
        Long totalStartup = startupsInterfaces.getTotalStartup();
        return ResponseEntity.ok(totalStartup);
    }
}
