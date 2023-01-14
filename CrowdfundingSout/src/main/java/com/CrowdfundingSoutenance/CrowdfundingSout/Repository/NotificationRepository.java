package com.CrowdfundingSoutenance.CrowdfundingSout.Repository;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
