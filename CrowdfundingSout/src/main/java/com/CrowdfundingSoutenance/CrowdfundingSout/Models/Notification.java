package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idnotifi;
    private String Message;
    private Date datenotif;


    @ManyToMany(mappedBy = "notifications")
    private Set<Investisseur> investisseurs = new HashSet<>();
}
