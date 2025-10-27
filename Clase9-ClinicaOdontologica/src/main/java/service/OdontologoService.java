package service;

import entity.Odontologo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.OdontologoRepository;

import java.util.List;

@Service
public class OdontologoService {

    @Autowired
    private OdontologoRepository odontologoRepository;

    public Odontologo guardarOdontologo(Odontologo odontologo){

        return odontologoRepository.save(odontologo);
    }
    public Odontologo buscarOdontologoPorId(Integer id) {
        return odontologoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Odontólogo no encontrado con id: " + id));
    }
    public List<Odontologo> buscarOdontologo() {

        return odontologoRepository.findAll();
    }
    public void eliminarOdontologo(Integer id){

        odontologoRepository.deleteById(id);
    }
}
