package com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Notification;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Projets;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Typeprojet;

import java.util.List;

public interface NotificationServInter {
    String generateNotificationByType(Projets projets, Typeprojet typeprojet);
    List<Notification>findAll();
  //  List<Notification> getNotificationByInvestisseur(Long idUser);
}
