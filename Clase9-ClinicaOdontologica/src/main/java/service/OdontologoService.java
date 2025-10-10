package service;

import dao.iDao;
import model.Odontologo;
import java.util.List;

public class OdontologoService {
    private iDao<Odontologo> odontologoiDao;

    //Constructor
    public OdontologoService(iDao<Odontologo> odontologoiDao) {

        this.odontologoiDao = odontologoiDao;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo){
        return odontologoiDao.guardar(odontologo);
    }
    public Odontologo buscarOdontologoPorId(Integer id){

        return odontologoiDao.buscar(id);
    }

    public List<Odontologo> buscarOdontologo() {
        return odontologoiDao.buscarTodos();
    }

    public void eliminarOdontologo(Integer id){
        odontologoiDao.eliminar(id);
    }
}
