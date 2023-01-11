package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Typeprojet;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.TypeprojetInterfServ;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/typeprojets")
public class TypeprojetsController {
    @Autowired
    TypeprojetInterfServ typeprojetInterfServ;

    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody Typeprojet typeprojet){

        return  typeprojetInterfServ.save(typeprojet);
    }
    @GetMapping("typeparnom/{nomtype}")
    public List<Typeprojet> findByNomtype(@PathVariable String nomtype){
        return typeprojetInterfServ.findByNomtype(nomtype);
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
