package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Pret;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.PretInterfaceServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/pret")
public class PretController {

    @Autowired
    private PretInterfaceServ pretInterfaceServ;

    @PostMapping("/add")
    public Pret save(Pret pret) {
        return pretInterfaceServ.savepret(pret);
    }

    @PutMapping("modf/{idPret}")
    public ResponseEntity<Pret> updatePretById(@PathVariable Long idPret,
                                               @RequestBody Pret pret) {
        Optional<Pret> pret1 = Optional.ofNullable(pretInterfaceServ.getPretById(idPret));
        if (pret1.isPresent()) {
            Pret _pret = pret1.get();
            _pret.setPretmaximun(pret.getPretmaximun());
            _pret.setPretminimun(pret.getPretminimun());
            _pret.setPourcentage(pret.getPourcentage());
            return new ResponseEntity<>(pretInterfaceServ.savepret(_pret), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idPret}")
    public void deletePret(@PathVariable Long idPret) {
        pretInterfaceServ.deletePret(idPret);
    }

    @GetMapping("affpret/{idPret}")
    public Pret findPretById(@PathVariable Long idPret) {
        return pretInterfaceServ.getPretById(idPret);
    }

    @GetMapping("affipret")
    public List<Pret> findpretAll() {
        return pretInterfaceServ.getAllPrets();
    }
}
