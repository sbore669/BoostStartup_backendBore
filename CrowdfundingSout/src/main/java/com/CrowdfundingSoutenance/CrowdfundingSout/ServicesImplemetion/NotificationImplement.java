package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Notification;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Typeprojet;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.InvestisseurReposotory;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.NotificationRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.NotificationServInter;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.response.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationImplement implements NotificationServInter {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    InvestisseurReposotory investisseurReposotory;
    @Override
    public String generateNotificationByType(Projets projets, Typeprojet typeprojet) {
        String message = "Un nouveau projet de type " + typeprojet.getNomtype() + " vient d'être lancé, " +
                 projets.getNomprojets() + " .Nous vous invitons à consulter les détails de ce projet. ";
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDatenotif(new Date());
        notificationRepository.save(notification);
        for (Investisseur investisseur : typeprojet.getInvestisseurs()){
            investisseur.getNotifications().add(notification);
            investisseurReposotory.save(investisseur);
        }
        return "Notification créé avec succès";
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

}
