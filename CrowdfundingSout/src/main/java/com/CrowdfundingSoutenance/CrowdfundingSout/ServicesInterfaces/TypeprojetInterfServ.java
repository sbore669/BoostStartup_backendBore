package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Typeprojet;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TypeprojetInterfServ {
    ResponseEntity<MessageResponse> save (Typeprojet typeprojet);
    List<Typeprojet> findByNomtype(String nomtype);
    Typeprojet updateById(Long Idtypeprojets, Typeprojet typeprojet);
    void deleteById(Long Idtypeprojets);
    List<Typeprojet> getAllTypeprojet();
    Typeprojet getTypeprojetsById(Long Idtypeprojets);
}
