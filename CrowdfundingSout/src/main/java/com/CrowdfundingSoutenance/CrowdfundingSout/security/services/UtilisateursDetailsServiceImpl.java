package com.CrowdfundingSoutenance.CrowdfundingSout.security.services;

import com.CrowdfundingSoutenance.CrowdfundingSout.Models.Utilisateurs;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.RoleRepository;
import com.CrowdfundingSoutenance.CrowdfundingSout.Repository.UtilisateursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/*

UserDetailsServiceest décrit comme une interface principale qui charge des données spécifiques à
l'utilisateur dans la documentation Spring.

 */

@Service
public class UtilisateursDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UtilisateursRepository utilisateursRepository;

  @Autowired
  RoleRepository roleRepository;

  //recupere les details du collaborateur
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Utilisateurs user = utilisateursRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("collaborateur non trouvé: " + username));

    return UtilisateursDetails.build(user);
  }




}
