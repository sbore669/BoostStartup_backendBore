package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Methodepaiement;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.MethpaieInterfaceServ;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/methopaie/")
@CrossOrigin(origins = "http://localhost:8100/", maxAge = 3600,allowCredentials = "true")
public class MethodepaieControllers {

    @Autowired
    MethpaieInterfaceServ methpaieInterfaceServ;

    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody Methodepaiement methodepaiement) {
        try {
            Methodepaiement savedMethod = methpaieInterfaceServ.save(methodepaiement);
            return new ResponseEntity<>(new MessageResponse("Methode de paiement"+methodepaiement+"Ajouter avec succes"), HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new MessageResponse("cette methode de paiement existe deja"), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/modif/{idpaiement}")
    public String updateMethodeById(@PathVariable Long idpaiement,
                                    @RequestBody Methodepaiement methodepaiement){
        return methpaieInterfaceServ.updateMethodById(idpaiement, methodepaiement);
    }

    @GetMapping("/{nompaiement}")
    public Methodepaiement getMethoByName(@PathVariable String nompaiement){
        return methpaieInterfaceServ.getMethoByName(nompaiement);
    }
    @DeleteMapping("/delete/{idpaiement}")
    public void suprimerParId(@PathVariable Long idpaiement){
        methpaieInterfaceServ.deleteById(idpaiement);
    }
    @GetMapping("/all")
    public List<Methodepaiement> toutlesmethode(){
        return methpaieInterfaceServ.findAll();
    }


}
