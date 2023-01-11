package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Realisation;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.RealisationInterface;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.Autres.SaveImage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/realisation/")
public class RealisationControllers {

    @Autowired
    RealisationInterface realisationInterface;

    @PostMapping("/addrealisation")
    public String createRealisation(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam (value = "donnerealisation") String donnerealisation) throws IOException {
        if(file != null){

            ObjectMapper mapper = new ObjectMapper();
            Realisation realisation = mapper.readValue(donnerealisation, Realisation.class);
            String nomfile = StringUtils.cleanPath(file.getOriginalFilename());
            realisation.setDaterealisation(new Date());
            realisation.setImageRealisation(SaveImage.save(file, nomfile));
            realisationInterface.createRealisation(realisation);
            return "realisation ajouter avec succès";
        }
       return "Erirrrrrrrrrrrrrrrrrrrrrrrrrrrr";
    }
    @PutMapping("/update/{IdRealisation}")
    public String updateRealisationById(@PathVariable Long IdRealisation, @RequestBody Realisation realisation) {
        return realisationInterface.UpdateRealisationById(IdRealisation, realisation);
    }
    @GetMapping("/{IdRealisation}")
    public Realisation getRealisationById(@PathVariable Long IdRealisation) {
        return realisationInterface.getRealisationById(IdRealisation);
    }
    @GetMapping("/all")
    public List<Realisation> getAllRealisation() {
        return realisationInterface.getAllRealisation();
    }
    @GetMapping("/voir/{nomRealisation}")
    public List<Realisation> getRealisationByName(@PathVariable String nomRealisation) {
        return realisationInterface.getRealisationByName(nomRealisation);
    }
    @DeleteMapping("/delete/{IdRealisation}")
    public void deleteRealisation(@PathVariable Long IdRealisation) {
        realisationInterface.deleteRealisation(IdRealisation);
    }



}
