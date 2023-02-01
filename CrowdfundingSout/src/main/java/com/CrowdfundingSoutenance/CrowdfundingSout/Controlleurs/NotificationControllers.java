package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Notification;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.NotificationRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.NotificationServInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/notification")
@CrossOrigin(origins = "http://localhost:8100/", maxAge = 3600,allowCredentials = "true")
public class NotificationControllers {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationServInter notificationServInter;

 /*   @GetMapping("/invest/{IdUsers}")
    public ResponseEntity <List<Notification>> getByInvestisseur(Long idUser){

        List<Notification> notificationlist = notificationServInter.getNotificationByInvestisseur(idUser);

        if (notificationlist.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notificationlist, HttpStatus.OK);
    }*/

}
