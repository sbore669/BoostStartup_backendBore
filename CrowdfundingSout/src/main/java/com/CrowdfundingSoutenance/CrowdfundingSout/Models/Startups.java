package com.CrowdfundingSoutenance.CrowdfundingSout.Models;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Enum.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Startups extends Utilisateurs {



    @NotBlank
    @Size(max = 100)
    private String nomStartups;

    @NotBlank
    @Size(max = 100)
    private String contact;

    @NotBlank
    @Email
    @Size(max = 100)
    private String emailStartups;

    @NotBlank
    @Size(max = 100)
    private String secteurActivite;

    @NotBlank
    @Size(max = 100)
    private String stadeDeveloppement;

    @NotBlank
    @Size(max = 100)
    private String numeroIdentification;

    @NotBlank
    @Size(max = 1000)
    private String descriptionStartups;

    @NotNull
    private Date dateCreation;

    @NotBlank
    @Size(max = 100)
    private String proprietaire;

    @NotBlank
    @Size(max = 100)
    private String formeJuridique;

    @NotBlank
    @Size(max = 100)
    private String chiffreAffaire;

    @NotBlank
    @Size(max = 100)
    private String localisation;

    @NotBlank
    @Size(max = 100)
    private String pays;


    @Enumerated(EnumType.STRING)
    private Status status;



    // constructeur
    public Startups(String username, String email, String password, String adresse, String nomComplet, String photo,
                    String nomStartups, String contact, String emailStartups, String secteurActivite,
                    String stadeDeveloppement, String numeroIdentification, String descriptionStartups, Date dateCreation,
                    String proprietaire, String formeJuridique, String chiffreAffaire, String localisation, String pays,
                    Status status) {
        super(username, email,password, adresse, nomComplet, photo);
        this.nomStartups = nomStartups;
        this.contact = contact;
        this.emailStartups = emailStartups;
        this.secteurActivite = secteurActivite;
        this.stadeDeveloppement = stadeDeveloppement;
        this.numeroIdentification = numeroIdentification;
        this.descriptionStartups = descriptionStartups;
        this.dateCreation = dateCreation;
        this.proprietaire = proprietaire;
        this.formeJuridique = formeJuridique;
        this.chiffreAffaire = chiffreAffaire;
        this.localisation = localisation;
        this.pays = pays;
        this.status = status;
    }
}
