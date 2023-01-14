package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Investisseur extends Utilisateurs{
    private Long totalInvestissement;
    private Long telephone;


    @ManyToMany(mappedBy = "investisseurs")
    private Set<Typeprojet> typeprojet = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "investisseur_notification",
            joinColumns = @JoinColumn(name = "investisseur_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"))
    private Set<Notification> notifications = new HashSet<>();


}

