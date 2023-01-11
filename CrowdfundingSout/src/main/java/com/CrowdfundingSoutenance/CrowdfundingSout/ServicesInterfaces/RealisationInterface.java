package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Realisation;

import java.util.List;

public interface RealisationInterface {
    String createRealisation(Realisation realisation);
    String UpdateRealisationById(Long IdRealisation, Realisation realisation);
    Realisation getRealisationById(Long IdRealisation);
    List<Realisation> getAllRealisation();
    List<Realisation> getRealisationByName(String nomRealisation);
    void deleteRealisation(Long id);
}
