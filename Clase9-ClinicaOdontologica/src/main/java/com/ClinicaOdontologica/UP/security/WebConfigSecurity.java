package com.ClinicaOdontologica.UP.security;

import com.ClinicaOdontologica.UP.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests((authz)-> authz
                        // Recursos estáticos
                        .requestMatchers("/js/**").permitAll()

                        // Paginas publicas
                        .requestMatchers("/", "/get_*.html", "/login.html").permitAll()
                        //Para poder hacer consultas H2
                        .requestMatchers(PathRequest.toH2Console()).permitAll()


                        //Todos los metodos del CRUD (sin read) debe estar el usuario loggeado
                        .requestMatchers("/index.html").hasRole("USER")
                        .requestMatchers("/post_*.html", "/put_*.html", "/del_*.html").hasRole("USER")
                        .requestMatchers("/paciente/**", "/odontologo/**", "/turno/**").hasRole("USER")

                        .anyRequest().authenticated())

                // Configuración del login personalizado
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login") // donde Spring procesa el form
                        .defaultSuccessUrl("/index.html", true)
                        .failureUrl("/login.html?error")
                        .permitAll()
                )
                // Configuración de logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login.html?logout")
                        .permitAll()
                );
        return http.build();
    }
}
