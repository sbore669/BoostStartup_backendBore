package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Realisation;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.RealisationRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.RealisationInterface;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class RealisationServImplementa implements RealisationInterface {

    @Autowired
    RealisationRepository realisationRepository;

    @Override
    public Realisation createRealisation(Realisation realisation) {
        return realisationRepository.save(realisation);
    }

    @Override
    public Realisation UpdateRealisationById(Long id, Realisation realisation) {
        return null;
    }

    @Override
    public Realisation getRealisationById(Long id) {
        return realisationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Realisation> getAllRealisation() {
        return realisationRepository.findAll();
    }

    @Override
    public List<Realisation> getRealisationByName(String name) {
        return realisationRepository.findByNomRealisation(name);
    }

    @Override
    public void deleteRealisation(Long id) {
        realisationRepository.deleteById(id);
    }
}
