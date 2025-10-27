package dao;

import entity.Domicilio;
import entity.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAOH2 implements iDao<Paciente>{
    private static final String SQL_SELECT_ONE=" SELECT * FROM PACIENTES WHERE ID=?";
    private static final String SQL_INSERT_ONE="INSERT INTO PACIENTES (NOMBRE, APELLIDO, NUMEROCONTACTO, FECHAINGRESO, DOMICILIO_ID, EMAIL) VALUES (?,?,?,?,?,?)";
    private static final String SQL_SELECT_ALL ="SELECT * FROM PACIENTES";
    private static final String SQL_UPDATE_ONE="UPDATE PACIENTES SET NOMBRE=?, APELLIDO=?, NUMEROCONTACTO=?, EMAIL=? WHERE ID=?";
    private static final String SQL_DELETE_ONE="DELETE FROM PACIENTES WHERE ID=?";

    @Override
    public Paciente guardar(Paciente paciente) {
        Connection connection=null;
        try{
            connection=BD.getConnection();

            Statement statement = connection.createStatement();
            PreparedStatement ps_insert_one = connection.prepareStatement(SQL_INSERT_ONE, Statement.RETURN_GENERATED_KEYS);

            ps_insert_one.setString(1, paciente.getNombre());
            ps_insert_one.setString(2, paciente.getApellido());
            ps_insert_one.setInt(3, paciente.getNumeroContacto());
            ps_insert_one.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            ps_insert_one.setInt(5, paciente.getDomicilio().getId());
            ps_insert_one.setString(6, paciente.getEmail());

            ps_insert_one.executeUpdate();

            // tomo ID autogenerado
            try (ResultSet rs = ps_insert_one.getGeneratedKeys()) {
                if (rs.next()) {
                    paciente.setId(rs.getInt(1));
                }
            }

            System.out.println("Paciente creado");
            return paciente;

        } catch (Exception e) {
            e.printStackTrace();
            return paciente;
        }
    }


    @Override
    public Paciente buscar(Integer id) {
        Connection connection=null;
        Paciente paciente= null;
        Domicilio domicilio= null;
        try{
            connection=BD.getConnection();
            //statement mundo java a sql
            Statement statement= connection.createStatement();
            PreparedStatement ps_select_one= connection.prepareStatement(SQL_SELECT_ONE);
            ps_select_one.setInt(1,id);
            //ResultSet mundo bdd a java
            ResultSet rs= ps_select_one.executeQuery();
            DomicilioDAOH2 daoAux= new DomicilioDAOH2();
            while(rs.next()){
                domicilio=daoAux.buscar(rs.getInt(6));
                paciente= new Paciente(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getDate(5).toLocalDate(),domicilio,rs.getString(7));
            }
        }catch (Exception e){
            e.getMessage();
        }
        System.out.println("paciente encontrado");
        return paciente;
    }

    @Override
    public void eliminar(Integer id) {
        Connection connection=null;
        try{
            connection=BD.getConnection();

            Statement statement = connection.createStatement();
            PreparedStatement ps_delete_one = connection.prepareStatement(SQL_DELETE_ONE, Statement.RETURN_GENERATED_KEYS);
            ps_delete_one.setInt(1, id);

            int filas = ps_delete_one.executeUpdate();
            if (filas > 0) {
                System.out.println("Paciente eliminado correctamente (ID=" + id + ")");
            }else{
                System.out.println("No se encontró paciente con ID=" + id);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Paciente paciente) {
        Connection connection=null;
        try{
            connection=BD.getConnection();

            Statement statement = connection.createStatement();
            PreparedStatement ps_update_one = connection.prepareStatement(SQL_UPDATE_ONE, Statement.RETURN_GENERATED_KEYS);

            ps_update_one.setString(1, paciente.getNombre());
            ps_update_one.setString(2, paciente.getApellido());
            ps_update_one.setInt(3, paciente.getNumeroContacto());
            ps_update_one.setString(4, paciente.getEmail());
            ps_update_one.setInt(5, paciente.getId());

            ps_update_one.executeUpdate();
            System.out.println("Paciente Actualizado");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Paciente buscarGenerico(String parametro) {
        return null;
    }

    @Override
    public List<Paciente> buscarTodos() {
        Connection connection=null;
        List<Paciente> lista = new ArrayList<>();
        Domicilio domicilio= null;
        Paciente paciente = null;
        try{
            connection=BD.getConnection();
            //statement mundo java a sql
            Statement statement= connection.createStatement();
            PreparedStatement ps_select_all= connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet rs= ps_select_all.executeQuery();
            DomicilioDAOH2 daoAux= new DomicilioDAOH2();

            while(rs.next()){
                domicilio=daoAux.buscar(rs.getInt(6));
                paciente= new Paciente(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getDate(5).toLocalDate(),domicilio,rs.getString(7));
                lista.add(paciente);
            }
        }catch (Exception e){
            e.getMessage();
        }
        System.out.println("Lista de pacientes");
        return lista;
    }
}
