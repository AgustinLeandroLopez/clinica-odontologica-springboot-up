package com.ClinicaOdontologica.UP.security;

import com.ClinicaOdontologica.UP.entity.Usuario;
import com.ClinicaOdontologica.UP.entity.UsuarioRole;
import com.ClinicaOdontologica.UP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements ApplicationRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder codificador;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (usuarioRepository.findByEmail("agustin@gmail.com").isEmpty()) {
            Usuario user = new Usuario();
            user.setNombre("Agustin");
            user.setEmail("agustin@gmail.com");
            user.setPassword("admin");
            user.setUserName("agus");
            user.setUsuarioRole(UsuarioRole.ROLE_USER);
            usuarioRepository.save(user);
            System.out.println("Usuario inicial cargado");
        }
        else{
            System.out.println("Usuario 'agustin@gmail.com' ya existe. No se inserta de nuevo.");
        }
    }
}