package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Methodepaiement;

import java.util.List;

public interface MethpaieInterfaceServ {
    Methodepaiement save(Methodepaiement methodepaiement);
    String updateMethodById(Long idpaiement, Methodepaiement methodepaiement);
    Methodepaiement getMethoByName(String nompaiement);
    void deleteById(Long idpaiement);
    List<Methodepaiement> findAll();


}
