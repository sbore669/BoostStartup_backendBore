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
    public String createRealisation(Realisation realisation) {
        if (realisation != null) {
            realisationRepository.save(realisation);
            return "Realisation ajouter ";
        }
        return "Erreur";
    }

    @Override
    public String UpdateRealisationById(Long IdRealisation, Realisation realisation) {
        Realisation existingRealisation = realisationRepository.findById(IdRealisation).orElse(null);
        if (existingRealisation != null && realisation != null) {
            existingRealisation.setNomRealisation(realisation.getNomRealisation());
            existingRealisation.setMontantRealisation(realisation.getMontantRealisation());
            existingRealisation.setImageRealisation(realisation.getImageRealisation());
            existingRealisation.setDaterealisation(realisation.getDaterealisation());
            realisationRepository.save(existingRealisation);

            return "Réalisation modifier avec succès";
        }
        return "Realisation non modifier";
    }

    @Override
    public Realisation getRealisationById(Long IdRealisation) {
        return realisationRepository.findById(IdRealisation).orElse(null);
    }

    @Override
    public List<Realisation> getAllRealisation() {
        return realisationRepository.findAll();
    }

    @Override
    public List<Realisation> getRealisationByName(String nomRealisation) {
        if(nomRealisation != null && !nomRealisation.isEmpty()){
            return realisationRepository.findByNomRealisation(nomRealisation);
        }
        return null;
    }

    @Override
    public void deleteRealisation(Long id) {
        Realisation existingRealisation = realisationRepository.findById(id).orElse(null);
        if(existingRealisation != null){
            realisationRepository.deleteById(id);
        }
    }
}
