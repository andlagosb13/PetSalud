package cl.duoc.semana5.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cl.duoc.semana5.model.Usuario;
import cl.duoc.semana5.repository.UsuarioRepo;
import cl.duoc.semana5.security.model.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UsuarioRepo usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    return new CustomUserDetails(usuario);
  }

}
