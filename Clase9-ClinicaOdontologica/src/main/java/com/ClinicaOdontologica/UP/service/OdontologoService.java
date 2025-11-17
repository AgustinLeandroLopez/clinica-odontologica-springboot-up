package com.ClinicaOdontologica.UP.service;

import com.ClinicaOdontologica.UP.entity.Odontologo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ClinicaOdontologica.UP.repository.OdontologoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {

    @Autowired
    private OdontologoRepository odontologoRepository;

    public Odontologo guardarOdontologo(Odontologo odontologo){

        return odontologoRepository.save(odontologo);
    }

    public Odontologo actualizarOdontologo(Odontologo odontologo){

        return odontologoRepository.save(odontologo);
    }

    public Optional<Odontologo> buscarOdontologoPorId(Integer id) {
        return odontologoRepository.findById(id);
    }
    public List<Odontologo> buscarOdontologos() {

        return odontologoRepository.findAll();
    }
    public void eliminarOdontologo(Integer id){

        odontologoRepository.deleteById(id);
    }

    public Optional<Odontologo> buscarPorMatricula(Integer matricula){
        return odontologoRepository.findByMatricula(matricula);
    }
}
