package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.*;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Enum.ERole;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Enum.Status;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.RoleRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.StartupsRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.UtilisateursRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion.StartupsImplemation;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.Autres.ConfigImages;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.request.LoginRequest;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.request.SignupRequest;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.response.JwtResponse;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.response.MessageResponse;
import com.CrowdfundingSoutenance.CrowdfundingSout.security.jwt.JwtUtils;
import com.CrowdfundingSoutenance.CrowdfundingSout.security.services.UtilisateursDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private static final Logger log = LoggerFactory.getLogger(AuthController.class);

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UtilisateursRepository utilisateursRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  StartupsRepository startupsRepository;

  @Autowired
  private StartupsImplemation startupsImplemation;

  //encoder du password
  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  //@Valid assure la validation de l'ensemble de l'objet
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    String url = "C:/Users/sbbore/Pictures/springimages";

    /*
    String nomfile = StringUtils.cleanPath(file.getOriginalFilename());
    System.out.println(nomfile);
    ConfigImage.saveimgA(url, nomfile, file);
    */


    /*
     AuthenticationManager est comme un coordinateur où vous pouvez enregistrer plusieurs fournisseurs et,
     en fonction du type de demande, il enverra une demande d'authentification au bon fournisseur.
     */

    //authenticate effectue l'authentification avec la requête.

     /*
       AuthenticationManager utilise DaoAuthenticationProvider(avec l'aide de
       UserDetailsService& PasswordEncoder) pour valider l'instance de UsernamePasswordAuthenticationToken,
       puis renvoie une instance entièrement remplie Authenticationen cas d'authentification réussie.
     */

    Authentication authentication = authenticationManager.authenticate(
            //on lui fournit un objet avec username et password fournit par l'admin
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    /*
      SecurityContext et SecurityContextHolder sont deux classes fondamentales de Spring Security .
      Le SecurityContext est utilisé pour stocker les détails de l'utilisateur
      actuellement authentifié, également appelé principe. Donc, si vous devez obtenir
      le nom d'utilisateur ou tout autre détail d'utilisateur, vous devez d'abord obtenir
      ce SecurityContext . Le SecurityContextHolder est une classe d'assistance qui permet
      d'accéder au contexte de sécurité.
     */

    //on stocke les informations de connexion de l'utilisateur actuelle souhaiter se connecter dans SecurityContextHolder
    SecurityContextHolder.getContext().setAuthentication(authentication);

    //on envoie encore les infos au generateur du token
    String jwt = jwtUtils.generateJwtToken(authentication);

    //on recupere les infos de l'user
    UtilisateursDetails utilisateursDetails = (UtilisateursDetails) authentication.getPrincipal();

    //on recupere les roles de l'users
    List<String> roles = utilisateursDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    log.info("conexion controlleur");

    //on retourne une reponse, contenant l'id username, e-mail et le role du collaborateur
    return ResponseEntity.ok(new JwtResponse(jwt,
                         utilisateursDetails.getId(),
                         utilisateursDetails.getUsername(),
                         utilisateursDetails.getEmail(), roles,
                         utilisateursDetails.getAdresse(),
                         utilisateursDetails.getNomcomplet(),
                         utilisateursDetails.getPhoto()
                         ));
  }

  //@PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/signup")//@valid s'assure que les données soit validées
  public ResponseEntity<?> registerUser(@Valid @RequestParam(value = "file", required = true) MultipartFile file,
                                        @Valid  @RequestParam(value = "donneesuser") String donneesuser) throws IOException {

    //chemin de stockage des images
    String url = "C:/Users/sbbore/Pictures/springimages";

    //recupere le nom de l'image
    String nomfile = StringUtils.cleanPath(file.getOriginalFilename());
    System.out.println(nomfile);

    //envoie le nom, url et le fichier à la classe ConfigImages qui se chargera de sauvegarder l'image
    ConfigImages.saveimg(url, nomfile, file);

    //converssion du string reçu en classe SignupRequest
    SignupRequest signUpRequest = new JsonMapper().readValue(donneesuser, SignupRequest.class);

    signUpRequest.setPhoto(nomfile);

    if (utilisateursRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
    }

    if (utilisateursRepository.existsByEmail(signUpRequest.getEmail())) {

      //confectionne l'objet de retour à partir de ResponseEntity(une classe de spring boot) et MessageResponse
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Erreur: Cet email est déjà utilisé!"));
    }

    // Create new user's account
    Utilisateurs utilisateurs = new Utilisateurs(signUpRequest.getUsername(),
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()), signUpRequest.getAdresse(),
                signUpRequest.getNomcomplet(), signUpRequest.getPhoto()
            );

    //on recupere le role de l'user dans un tableau ordonné de type string
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      System.out.println("####################################" + signUpRequest.getRole() + "###########################################");

      //on recupere le role de l'utilisateur
      Role userRole = roleRepository.findByName(ERole.ROLE_USER);
      roles.add(userRole);//on ajoute le role de l'user à roles
    } else {
      strRoles.forEach(role -> {//on parcours le role
        switch (role) {
        case "admin"://si le role est à égale à admin
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
          roles.add(adminRole);

          break;
        default://dans le cas écheant

          //on recupere le role de l'utilisateur
          Role userRole = roleRepository.findByName(ERole.ROLE_USER);
          roles.add(userRole);
        }
      });
    }

    //on ajoute le role au collaborateur
    utilisateurs.setRoles(roles);
    utilisateursRepository.save(utilisateurs);

    return ResponseEntity.ok(new MessageResponse("Collaborateur enregistré avec succès!"));
  }

  //Création d'un compte startups
  //@PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/inscrpStart")//@valid s'assure que les données soit validées
  public ResponseEntity<?> registerStartups(
          @Valid @RequestParam(value = "file", required = true) MultipartFile file,

          @Valid @RequestParam(value = "donneesstartups")String donneesstartups) throws IOException{

    //chemin de stockage des images
    String url = "C:/Users/sbbore/Pictures/springimages";

    //@Valid  @RequestParam(value = "donneesuser") String donneesuser,

    //recupere le nom de l'image
    String nomfile = StringUtils.cleanPath(file.getOriginalFilename());
    System.out.println(nomfile);

    //envoie le nom, url et le fichier à la classe ConfigImages qui se chargera de sauvegarder l'image
    ConfigImages.saveimg(url, nomfile, file);

    //converssion du string reçu en classe SignupRequest
    ObjectMapper mapper = new ObjectMapper();
    Startups startups = mapper.readValue(donneesstartups, Startups.class);

    //SignupRequest signUpRequest = new JsonMapper().readValue(donneesuser, SignupRequest.class);

    //signUpRequest.setPhoto(nomfile);

    /*if (utilisateursRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
    }*/

    //if (utilisateursRepository.existsByEmail(signUpRequest.getEmail())) {

      //confectionne l'objet de retour à partir de ResponseEntity(une classe de spring boot) et MessageResponse
     /* return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Erreur: Cet email est déjà utilisé!"));
    }*/

    // Create new user's account
    /*Utilisateurs utilisateurs = new Utilisateurs(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()), signUpRequest.getAdresse(),
            signUpRequest.getNomcomplet(), signUpRequest.getPhoto()
    );*/

    //on recupere le role de l'user dans un tableau ordonné de type string
   /* Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();*/

    /*if (strRoles == null) {
      System.out.println("####################################" + signUpRequest.getRole() + "###########################################");

      //on recupere le role de l'utilisateur
      Role userRole = roleRepository.findByName(ERole.ROLE_STARTUPS);
      roles.add(userRole);//on ajoute le role de l'user à roles
    } else {
      strRoles.forEach(role -> {//on parcours le role
        switch (role) {
          case "admin"://si le role est à égale à admin
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
            roles.add(adminRole);

            break;
          default://dans le cas écheant

            //on recupere le role de l'utilisateur
            Role userRole = roleRepository.findByName(ERole.ROLE_USER);
            roles.add(userRole);
        }
      });
    }*/

    //on ajoute le role au collaborateur
    /*utilisateurs.setRoles(roles);
    utilisateursRepository.save(utilisateurs);*/
    startups.setPhoto(nomfile);
    if (startups.getStatus() == null){
      startups.setStatus(Status.ENCOURS);
    } else if (Arrays.asList(Status.values()).contains(startups.getStatus())) {
      throw new IllegalArgumentException("L'état de la startups est incorrect");
    }
    startups.setDateCreation(new Date());
    startups.setPassword(encoder.encode(startups.getPassword()));
    Set<Role> roles = new HashSet<>();
    Role role = roleRepository.findByName(ERole.ROLE_STARTUPS);
    roles.add(role);
    startups.setRoles(roles);
    startupsRepository.save(startups);

    return ResponseEntity.ok(new MessageResponse("Startup cree avec succès!"));
  }

 /* @PutMapping("/modifierStatups/{id}")
  public ResponseEntity<?> updateStartupso(@PathVariable Long id,
                                          @Valid @RequestParam(value = "file", required = true) MultipartFile file,
                                          @Valid @RequestParam(value = "donneesstartups") String donneesstartups) throws IOException {

    // récupère la Startup à mettre à jour à partir de la base de données
    Startups startups = startupsRepository.findById(id).orElse(null);
    if (startups == null) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Erreur: Startup introuvable!"));
    }

    // chemin de stockage des images
    String url = "C:/Users/sbbore/Pictures/springimages";

    // récupère le nom de l'image
    String nomfile = StringUtils.cleanPath(file.getOriginalFilename());
    System.out.println(nomfile);

    // envoie le nom, url et le fichier à la classe ConfigImages qui se chargera de sauvegarder l'image
    ConfigImages.saveimg(url, nomfile, file);

    // conversion du string reçu en classe Startups
    ObjectMapper mapper = new ObjectMapper();
    Startups updatedStartups = mapper.readValue(donneesstartups, Startups.class);

    // définit les champs de la Startup à mettre à jour en utilisant les valeurs de l'objet updatedStartups
    startups.setNomStartups(updatedStartups.getNomStartups());
    startups.setContact(updatedStartups.getContact());
    startups.setEmailStartups(updatedStartups.getEmailStartups());
    startups.setSecteurActivite(updatedStartups.getSecteurActivite());
    startups.setStadeDeveloppement(updatedStartups.getStadeDeveloppement());
    startups.setNumeroIdentification(updatedStartups.getNumeroIdentification());
    startups.setDescriptionStartups(updatedStartups.getDescriptionStartups());
    startups.setDateCreation(updatedStartups.getDateCreation());
    startups.setProprietaire(updatedStartups.getProprietaire());
    startups.setFormeJuridique(updatedStartups.getFormeJuridique());
    startups.setChiffreAffaire(updatedStartups.getChiffreAffaire());
    startups.setLocalisation(updatedStartups.getLocalisation());
    startups.setPays(updatedStartups.getPays());
    startups.setStatus(updatedStartups.getStatus());
    startups.setPhoto(nomfile);

    // enregistre la Startup mise à jour dans la base de données
    startupsRepository.save(startups);

    return ResponseEntity.ok(new MessageResponse("Startup mise à jour avec succès!"));
  }*/
 @PostMapping("/modifierStart/{id}")
 public ResponseEntity<?> updateStartupsById(
         @PathVariable Long id,
         @Valid @RequestParam(value = "file", required = true) MultipartFile file,
         @Valid @RequestParam(value = "donneesstartups") String donneesstartups) throws IOException {

   // récupère la Startup à mettre à jour à partir de la base de données
   Startups startups = startupsRepository.findById(id).orElse(null);
   if (startups == null) {
     return ResponseEntity
             .badRequest()
             .body(new MessageResponse("Erreur: Startup introuvable!"));
   }

   // chemin de stockage des images
   String url = "C:/Users/sbbore/Pictures/springimages";

   // récupère le nom de l'image
   String nomfile = StringUtils.cleanPath(file.getOriginalFilename());
   System.out.println(nomfile);

   // envoie le nom, url et le fichier à la classe ConfigImages qui se chargera de sauvegarder l'image
   ConfigImages.saveimg(url, nomfile, file);

   // conversion du string reçu en classe Startups
   ObjectMapper mapper = new ObjectMapper();
   Startups startupToUpdate = mapper.readValue(donneesstartups, Startups.class);

   // définit le nom de l'image pour la Startup
   startupToUpdate.setPhoto(nomfile);

   // si le status n'est pas défini, il est défini comme "EN_COURS"
   if (startupToUpdate.getStatus() == null) {
     startupToUpdate.setStatus(Status.ENCOURS);
   } else if (Arrays.asList(Status.values()).contains(startupToUpdate.getStatus())) {
     // vérifie que le statut est un des valeurs possibles
     // si c'est le cas, ne fait rien
   } else {
     // sinon, définit le statut comme "EN_COURS"
     startupToUpdate.setStatus(Status.ENCOURS);
   }
   startupToUpdate.setEmail(startupToUpdate.getEmail());
   startupToUpdate.setUsername(startupToUpdate.getUsername());
   startupToUpdate.setPhoto(nomfile);
   startupToUpdate.setPassword(encoder.encode(startups.getPassword()));
   startupToUpdate.setAdresse(startupToUpdate.getAdresse());
   startupToUpdate.setNomcomplet(startupToUpdate.getNomcomplet());

   // enregistre la Startup dans la base de données
   startupsImplemation.updateStartupsById(id, startupToUpdate);
   startupsRepository.save(startups);

   // confectionne l'objet de retour à partir de ResponseEntity (une classe de Spring Boot) et MessageResponse
   return ResponseEntity
           .ok(new MessageResponse("Startup modifier avec succès!"));
 }



}
