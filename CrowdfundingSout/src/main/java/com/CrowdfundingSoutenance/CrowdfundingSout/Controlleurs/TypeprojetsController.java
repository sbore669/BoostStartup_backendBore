package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Typeprojet;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.TypeProjetsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.TypeprojetInterfServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/typeprojets")
@CrossOrigin(origins = {"http://localhost:8100/", "http://localhost:4200/"}, maxAge = 3600,allowCredentials = "true")
public class TypeprojetsController {
    @Autowired
    TypeprojetInterfServ typeprojetInterfServ;

    @Autowired
    TypeProjetsRepository typeProjetsRepository;

    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody Typeprojet typeprojet){

        return  typeprojetInterfServ.save(typeprojet);
    }
    @GetMapping("/typeparnom/{nomtype}")
    public List<Typeprojet> findByNomtype(@PathVariable String nomtype){
        return typeprojetInterfServ.findByNomtype(nomtype);
    }

    @GetMapping("/typeparid/{typeprojet}")
    public Optional<Typeprojet> findById(@PathVariable Typeprojet typeprojet){
        return typeProjetsRepository.findById(typeprojet.getIdtypeprojets());
    }
    @GetMapping("/afficher")
    public List<Typeprojet> getAllTypeprojets(){
        return typeprojetInterfServ.getAllTypeprojet();
    }
    @PutMapping("/modf/{Idtypeprojets}")
    public ResponseEntity<Typeprojet> updateById(@PathVariable Long Idtypeprojets, @RequestBody Typeprojet typeprojet) {
        Typeprojet updatedTypeprojet = typeprojetInterfServ.updateById(Idtypeprojets, typeprojet);
        if (updatedTypeprojet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedTypeprojet, HttpStatus.OK);
    }
}
