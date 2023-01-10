package com.CrowdfundingSoutenance.CrowdfundingSout;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Enum.ERole;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Role;
import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Utilisateurs;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.RoleRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.UtilisateursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class CrowdfundingSoutApplication implements CommandLineRunner {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UtilisateursRepository utilisateursRepository;

	public CrowdfundingSoutApplication() {
	}

	public static void main(String[] args) {
		SpringApplication.run(CrowdfundingSoutApplication.class, args);
	}

	//***************************** METHODE PERMETTANT DE CREER UN ADMIN PAR DEFAUT **********
	@Override
	public void run(String... args) throws Exception {
		//VERIFICATION DE L'EXISTANCE DU ROLE ADMIN AVANT SA CREATION
		if (roleRepository.findAll().size() == 0){
			roleRepository.save(new Role(ERole.ROLE_ADMIN));
			roleRepository.save(new Role(ERole.ROLE_STARTUPS));
			roleRepository.save(new Role(ERole.ROLE_USER));
		}
		if (utilisateursRepository.findAll().size() == 0){
			Set<Role> roles = new HashSet<>();
			Role role = roleRepository.findByName(ERole.ROLE_ADMIN);
			roles.add(role);
			Utilisateurs utilisateurs = new Utilisateurs("Leweeskys","souleymanebore669@gmail.com", encoder.encode( "12345678"),"Lafiabougou","Souleymane Bore","photo.png");
			utilisateurs.setRoles(roles);
			utilisateursRepository.save(utilisateurs);

		}
	}

}
