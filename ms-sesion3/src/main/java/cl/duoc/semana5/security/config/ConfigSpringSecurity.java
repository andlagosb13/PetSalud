package cl.duoc.semana5.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtProperties.class)
public class ConfigSpringSecurity {

  @Autowired
  private cl.duoc.semana5.security.filter.JwtAuthenticationFilter jwtAuthenticationFilter;
  @Autowired
  private cl.duoc.semana5.security.service.CustomUserDetailsService customUserDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf((csrf) -> csrf.disable())
      .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // Permitir que la consola H2 se muestre en un iframe
      .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // establecer stateless
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.GET, "/api/productos").permitAll()
        .requestMatchers("/h2-console/**").permitAll() // Permitir acceso a la consola H2
        .requestMatchers("/api/auth/**").permitAll() // Permitir endpoints de autenticación (login/register)
        .anyRequest().authenticated()
      );

    // Añadir filtro JWT
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    // Registrar proveedor de autenticación DAO
    http.authenticationProvider(authenticationProvider());
    return http.build();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

}
