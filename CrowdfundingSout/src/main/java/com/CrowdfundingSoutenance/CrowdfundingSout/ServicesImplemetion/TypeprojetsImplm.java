package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Typeprojet;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.TypeProjetsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.TypeprojetInterfServ;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.response.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TypeprojetsImplm implements TypeprojetInterfServ {
    @Autowired
    TypeProjetsRepository typeProjetsRepository;
    @Override
    public ResponseEntity<MessageResponse> save(Typeprojet typeprojet) {
        if (typeProjetsRepository.existsByNomtype(typeprojet.getNomtype())){
        return ResponseEntity.badRequest().body(new MessageResponse("Type de projet existe deja "));
    }
        typeProjetsRepository.save(typeprojet);
        return ResponseEntity.ok(new MessageResponse("Type de projet ajouter avec succes"));
    }
    @Override
    public List<Typeprojet> findByNomtype(String nomtype) {
        return typeProjetsRepository.findByNomtype(nomtype);
    }


    @Override
    public Typeprojet updateById(Long Idtypeprojets, Typeprojet typeprojet) {
        Typeprojet typeprojet1 = typeProjetsRepository.findById(Idtypeprojets).orElse(null);
        if (typeprojet1 == null) {
            throw new IllegalArgumentException("Aucun type de projets trouvé avec l'ID spécifié");
        }
        typeprojet1.setNomtype(typeprojet.getNomtype());
        return typeProjetsRepository.save(typeprojet1);
    }

    @Override
    public void deleteById(Long Idtypeprojets) {
        typeProjetsRepository.deleteById(Idtypeprojets);
    }

    @Override
    public List<Typeprojet> findAll() {
        return typeProjetsRepository.findAll();
    }

    @Override
    public Typeprojet getTypeprojetsById(Long Idtypeprojets) {
        return typeProjetsRepository.findById(Idtypeprojets).orElse(null);
    }


}
