package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Startups;


import java.util.List;

public interface StartupsInterfaces {

    Startups createStartups(Startups startups);
    String updateStartupsById(Long id, Startups startups);
    void deleteStartups(Long id);
    Startups getStartupsById(Long id);
    List<Startups> getAllStartups();
    List<Startups> getStartupsByName(String name);

}
