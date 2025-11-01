package com.ClinicaOdontologica.UP.repository;

import com.ClinicaOdontologica.UP.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OdontologoRepository extends JpaRepository<Odontologo, Integer> {
    Optional<Odontologo> findByMatricula(Integer matricula);
}
