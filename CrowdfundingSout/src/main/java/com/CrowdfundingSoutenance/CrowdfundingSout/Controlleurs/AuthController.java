package com.CrowdfundingSoutenance.CrowdfundingSout.Controlleurs;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.*;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Enum.ERole;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Enum.Status;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.*;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesImplemetion.StartupsImplemation;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.StartupsInterfaces;
import com.CrowdfundingSoutenance.CrowdfundingSout.ServicesInterfaces.TypeprojetInterfServ;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.Autres.ConfigImages;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.Autres.EmailConstructor;
import com.CrowdfundingSoutenance.CrowdfundingSout.payload.Autres.SaveImage;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
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

@CrossOrigin(origins = {"http://localhost:8100/", "http://localhost:4200/"}, maxAge = 3600,allowCredentials = "true")
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
  InvestisseurReposotory investisseurReposotory;

  @Autowired
  StartupsRepository startupsRepository;

  @Autowired
  private StartupsImplemation startupsImplemation;

  @Autowired
  private StartupsInterfaces startupsInterfaces;
  @Autowired
  private TypeProjetsRepository typeProjetsRepository;

  //encoder du password
  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;
  @Autowired
  private TypeprojetInterfServ typeprojetInterfServ;

  @Autowired
  JavaMailSender mailSender;
  @Autowired
  EmailConstructor emailConstructor;

  //@Valid assure la validation de l'ensemble de l'objet
 // @PostMapping("/signin")
  //public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

   // String url = "C:/Users/sbbore/Pictures/springimages";

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

    //Authentication authentication = authenticationManager.authenticate(
            //on lui fournit un objet avec username et password fournit par l'admin
     //   new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    /*
      SecurityContext et SecurityContextHolder sont deux classes fondamentales de Spring Security .
      Le SecurityContext est utilisé pour stocker les détails de l'utilisateur
      actuellement authentifié, également appelé principe. Donc, si vous devez obtenir
      le nom d'utilisateur ou tout autre détail d'utilisateur, vous devez d'abord obtenir
      ce SecurityContext . Le SecurityContextHolder est une classe d'assistance qui permet
      d'accéder au contexte de sécurité.
     */

    //on stocke les informations de connexion de l'utilisateur actuelle souhaiter se connecter dans SecurityContextHolder
   // SecurityContextHolder.getContext().setAuthentication(authentication);

    //on envoie encore les infos au generateur du token
  //  String jwt = jwtUtils.generateJwtToken(authentication);

    //on recupere les infos de l'user
   // UtilisateursDetails utilisateursDetails = (UtilisateursDetails) authentication.getPrincipal();

    //on recupere les roles de l'users
   // List<String> roles = utilisateursDetails.getAuthorities().stream()
       // .map(item -> item.getAuthority())
       // .collect(Collectors.toList());

    //log.info("conexion controlleur");

    //on retourne une reponse, contenant l'id username, e-mail et le role du collaborateur
   /* return ResponseEntity.ok(new JwtResponse(jwt,
                         utilisateursDetails.getId(),
                         utilisateursDetails.getUsername(),
                         utilisateursDetails.getEmail(), roles,
                         utilisateursDetails.getAdresse(),
                         utilisateursDetails.getNomcomplet(),
                         utilisateursDetails.getPhoto()
                         ));*/
  //}



  @PostMapping("/signinn")
  public ResponseEntity<?> authenticateUserr(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UtilisateursDetails userDetails = (UtilisateursDetails) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    try {
      Startups user=startupsRepository.findByEmail(userDetails.getEmail());
      if(user.getStatus().equals(Status.ENCOURS)){
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Votre Startups est en attente de Validation"));
      }
    }catch (Exception e){

    }


    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(new JwtResponse
                    (userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getAdresse(),
                    userDetails.getPhoto(),
                    userDetails.getNomcomplet(),
                    roles));

  }







  //@PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/signup")//@valid s'assure que les données soit validées
  public ResponseEntity<?> registerUser(@Valid @RequestParam(value = "file", required = true) MultipartFile file,
                                        @Valid  @RequestParam(value = "donneesuser") String donneesuser) throws IOException {


    //recupere le nom de l'image
    String nomfile = StringUtils.cleanPath(file.getOriginalFilename());
    System.out.println(nomfile);

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
    //Enregistrement de l'image dans htdoc
    utilisateurs.setPhoto(SaveImage.save(file,nomfile));
    utilisateursRepository.save(utilisateurs);

    return ResponseEntity.ok(new MessageResponse("Collaborateur enregistré avec succès!"));
  }

  //Création d'un compte startups
  //@PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/inscrpStart")//@valid s'assure que les données soit validées
  public ResponseEntity<?> registerStartups(
          @Valid @RequestParam(value = "file", required = false) MultipartFile file,

          @Valid @RequestParam(value = "donneesstartups")String donneesstartups) throws IOException{

    String nomfile = StringUtils.cleanPath(file.getOriginalFilename());
   // System.out.println(nomfile);

    //envoie le nom, url et le fichier à la classe ConfigImages qui se chargera de sauvegarder l'image
    //ConfigImages.saveimg(url, nomfile, file);

    //converssion du string reçu en classe SignupRequest
    ObjectMapper mapper = new ObjectMapper();
    Startups startups = mapper.readValue(donneesstartups, Startups.class);



   // startups.setPhoto(nomfile);
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
    //Enregistrement de l'image dans htdoc
    startups.setPhoto(SaveImage.save(file,nomfile));
    startupsInterfaces.createStartups(startups);

    return ResponseEntity.ok(new MessageResponse("Startup cree avec succès!"));
  }


  @PutMapping("/Activerstart/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> ActiveStartups(@PathVariable Long id){
    Startups startupsActive = startupsRepository.findById(id).orElse(null);
    if (startupsActive != null){
      startupsActive.setStatus(Status.VALIDER);
      mailSender.send(emailConstructor.constructValidationStartups(startupsActive));
      startupsInterfaces.updateStartupsById(id, startupsActive);
      return ResponseEntity.ok(new MessageResponse("Startup activer avec succès!"));
    }
    return ResponseEntity
            .badRequest()
            .body(new MessageResponse("Erreur: Startup introuvable!"));
  }


  @PutMapping("/rejeterstart/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> RejeterStartups(@PathVariable Long id){
    Startups startupsActive = startupsRepository.findById(id).orElse(null);
    if (startupsActive != null){
      startupsActive.setStatus(Status.REJETER);
      startupsInterfaces.updateStartupsById(id, startupsActive);
      return ResponseEntity.ok(new MessageResponse("Startup rejeter avec succès!"));
    }
    return ResponseEntity
            .badRequest()
            .body(new MessageResponse("Erreur: Startup introuvable!"));
  }

 @PutMapping("/modifierStart/{id}")
 public ResponseEntity<?> updateStartupsById(
         @PathVariable Long id,
         @Valid @RequestParam(value = "file", required = false) MultipartFile file,
         @Valid @RequestParam(value = "donneesstartups") String donneesstartups) throws IOException {

   // récupère la Startup à mettre à jour à partir de la base de données
   Startups startups = startupsRepository.findById(id).orElse(null);
   if (startups == null) {
     return ResponseEntity
             .badRequest()
             .body(new MessageResponse("Erreur: Startup introuvable!"));
   }
   // récupère le nom de l'image
   String nomfile = StringUtils.cleanPath(file.getOriginalFilename());
   System.out.println(nomfile);


   // conversion du string reçu en classe Startups
   ObjectMapper mapper = new ObjectMapper();
   Startups startupToUpdate = mapper.readValue(donneesstartups, Startups.class);

   // si le status n'est pas défini, il est défini comme "EN_COURS"
   if (startupToUpdate.getStatus() == null) {
     startupToUpdate.setStatus(Status.ENCOURS);
   } else if (Arrays.asList(Status.values()).contains(startupToUpdate.getStatus())) {
     // vérifie que le statut est un des valeurs possibles
     // si c'est le cas, ne fait rien
   } else {
     // sinon, définit le statut comme "EN_COURS"
    // startupToUpdate.setStatus(Status.ENCOURS);
   }
  // Set<Role> roles = new HashSet<>();
   //Role role = roleRepository.findByName(ERole.ROLE_STARTUPS);
   //roles.add(role);
   startupToUpdate.setRoles(startups.getRoles());
   startupToUpdate.setEmail(startupToUpdate.getEmail());
   startupToUpdate.setUsername(startupToUpdate.getUsername());
   if(startupToUpdate.getPassword()!=null){
     startupToUpdate.setPassword(encoder.encode(startupToUpdate.getPassword()));

   }
   startupToUpdate.setAdresse(startupToUpdate.getAdresse());
   startupToUpdate.setNomcomplet(startupToUpdate.getNomcomplet());
   startupToUpdate.setPhoto(SaveImage.save(file,nomfile));


   // enregistre la Startup dans la base de données
   startupsInterfaces.updateStartupsById(id, startupToUpdate);
   startupsRepository.save(startups);

   // confectionne l'objet de retour à partir de ResponseEntity (une classe de Spring Boot) et MessageResponse
   return ResponseEntity
           .ok(new MessageResponse("Startup modifier avec succès!"));
 }

  @PostMapping("/InscrInvest/{Idtypeprojets}")//@valid s'assure que les données soit validées
  public ResponseEntity<?> registerInvestisseur(
          @PathVariable Long Idtypeprojets,
          @Valid @RequestParam(value = "file", required = true) MultipartFile file,
          @Valid @RequestParam(value = "donneesInvest")String donneesInvest) throws IOException{

    String nomfile = StringUtils.cleanPath(file.getOriginalFilename());
    System.out.println(nomfile);
    ObjectMapper mapper = new ObjectMapper();
    Investisseur investisseur = mapper.readValue(donneesInvest, Investisseur.class);

    if (utilisateursRepository.existsByUsername(investisseur.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
    }

    if (utilisateursRepository.existsByEmail(investisseur.getEmail())) {

      //confectionne l'objet de retour à partir de ResponseEntity(une classe de spring boot) et MessageResponse
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Erreur: Cet email est déjà utilisé!"));
    }
    Typeprojet typeprojet = typeprojetInterfServ.getTypeprojetsById(Idtypeprojets);
    if (typeprojet == null) {
      return new ResponseEntity<>("Type de projet introuvable", HttpStatus.NOT_FOUND);
    }
    /*if (projets.getPrettotalobtenu() == null){
      projets.setPrettotalobtenu(0L);
    }*/
    //  System.err.println(typeprojet.getIdtypeprojets());
    //investisseur.getTypeprojet().add(typeprojet);
    if (investisseur.getTotalInvestissement() == null){
      investisseur.setTotalInvestissement(0L);
    }
    investisseur.setPassword(encoder.encode(investisseur.getPassword()));
    Set<Role> roles = new HashSet<>();
    Role role = roleRepository.findByName(ERole.ROLE_USER);
    roles.add(role);
    investisseur.setRoles(roles);


    //Enregistrement de l'image dans htdoc
    investisseur.setPhoto(SaveImage.save(file,nomfile));

    typeprojet.getInvestisseurs().add(investisseurReposotory.save(investisseur));
    typeProjetsRepository.save(typeprojet);
    return ResponseEntity.ok(new MessageResponse("Compte investisseur creer avec succès!"));
  }


}
