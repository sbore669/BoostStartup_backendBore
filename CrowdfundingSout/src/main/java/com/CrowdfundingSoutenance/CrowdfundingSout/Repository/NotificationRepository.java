package com.CrowdfundingSoutenance.CrowdfundingSout.Repository;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Investisseur;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
   // List<Notification> findNotificationByInvestisseur(Investisseur investisseurs);
}
