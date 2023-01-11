package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Methodepaiement;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.MethpaieInterfaceServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/methopaie")
public class MethodepaieControllers {

    @Autowired
    MethpaieInterfaceServ methpaieInterfaceServ;

    @PostMapping("/add")
    public Methodepaiement save(@RequestBody Methodepaiement methodepaiement){
        return methpaieInterfaceServ.save(methodepaiement);
    }


}
