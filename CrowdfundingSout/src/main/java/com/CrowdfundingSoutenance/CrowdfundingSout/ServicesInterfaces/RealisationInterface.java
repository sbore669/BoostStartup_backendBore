package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Realisation;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Startups;

import java.util.List;

public interface RealisationInterface {
    Realisation createRealisation(Realisation realisation);
    Realisation UpdateRealisationById(Long id, Realisation realisation);
    Realisation getRealisationById(Long id);
    List<Realisation> getAllRealisation();
    List<Realisation> getRealisationByName(String name);
    void deleteRealisation(Long id);
}
