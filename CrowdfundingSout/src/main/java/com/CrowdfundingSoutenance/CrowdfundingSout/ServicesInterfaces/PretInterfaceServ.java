package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Pret;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;

import java.util.List;

public interface PretInterfaceServ {
    Pret savepret(Pret pret);
    Pret updatepret(Pret pret);
    void deletePret(Long id);
    Pret getPretById(Long id);
    List<Pret> getAllPrets();
}
