package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Enum.Status;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Startups;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.StartupsInterfaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/startup")
@CrossOrigin(origins = "http://localhost:8100/", maxAge = 3600,allowCredentials = "true")
public class StartupControllers {

    @Autowired
    StartupsInterfaces startupsInterfaces;

    @GetMapping("/status/VALIDER")
    public List<Startups> getStartupsByStatus() {
        return startupsInterfaces.findByStatus(Status.VALIDER);
    }

    @GetMapping("/affall")
    public List<Startups> getAllStartups() {
        return startupsInterfaces.getAllStartups();
    }
}
