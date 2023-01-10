package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Pret;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.PretRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.PretInterfaceServ;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PretImplement implements PretInterfaceServ {

    @Autowired
    PretRepository pretRepository;
    @Override
    public Pret savepret(Pret pret) {
        return pretRepository.save(pret);
    }

    @Override
    public Pret updatepret(Pret pret) {
        return pretRepository.save(pret);
    }

    @Override
    public void deletePret(Long id) {
        pretRepository.deleteById(id);
    }

    @Override
    public Pret getPretById(Long id) {
        return pretRepository.findById(id).orElse(null);
    }

    @Override
    public List<Pret> getAllPrets() {
        return pretRepository.findAll();
    }
}
