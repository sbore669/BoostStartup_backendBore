package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Methodepaiement;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.MethopaiemRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.MethpaieInterfaceServ;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class MethodServImplemt implements MethpaieInterfaceServ {

    @Autowired
    MethopaiemRepository methopaiemRepository;
    @Override
    public Methodepaiement save(Methodepaiement methodepaiement) {
        return methopaiemRepository.save(methodepaiement);
    }

    @Override
    public String updateMethodById(Long idpaiement, Methodepaiement methodepaiement) {
        Methodepaiement metho1 = methopaiemRepository.findById(idpaiement).orElse(null);
        if (metho1 != null) {
            metho1.setNompaiement(methodepaiement.getNompaiement());
            metho1.setAdressepaiement(methodepaiement.getAdressepaiement());
            metho1.setImagespaiement(methodepaiement.getImagespaiement());
            return "Methode de paiement modifier avec succès";
        }
        return "Methode de paiement introuvable";
    }

    @Override
    public Methodepaiement getMethoByName(String nompaiement) {
        return methopaiemRepository.findByNompaiement(nompaiement);
    }

    @Override
    public void deleteById(Long idpaiement) {
        methopaiemRepository.deleteById(idpaiement);
    }

    @Override
    public List<Methodepaiement> findAll() {
        return methopaiemRepository.findAll();
    }
}
