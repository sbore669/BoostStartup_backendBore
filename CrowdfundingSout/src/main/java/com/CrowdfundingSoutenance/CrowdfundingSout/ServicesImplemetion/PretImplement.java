package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Pret;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.PretRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.PretInterfaceServ;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PretImplement implements PretInterfaceServ {

    @Autowired
    PretRepository pretRepository;


    @Override
    public  Pret faireunpret(Projets projets, Long montantInvest, Investisseur investisseur) {
      /*  if (projets.getPrettotalobtenu() >= projets.getPret_maximun()){
            throw new IllegalArgumentException("cet projets n'accepte plus de prêt car le montant maximun de pret est atteint");
        } */
        if (projets.getPret_minimun() == 0  && projets.getPret_maximun() == 0 ){
            throw new IllegalArgumentException("cet projets n'accepte pas de prêt");
        }


        if (montantInvest >= projets.getPret_minimun() && montantInvest <= projets.getPret_maximun()){
            double pourcentage = (double) projets.getPourcentage()/100;
            double retourInvestissement = montantInvest * (1 + pourcentage);

            Pret pret = new Pret();
            pret.setProjets(projets);
            pret.setDate_investissement(new Date());
            pret.setPourcentage(projets.getPourcentage());
            pret.setRetourinvestissement(retourInvestissement);
            pret.setInvestisseur(investisseur);
            pret.setMontantInvest(montantInvest);


            return pretRepository.save(pret);
        } else {
            throw new IllegalArgumentException("Le montant de l'investissemnet ne respecte pas les limites minimale et maximale du projets");
        }
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
