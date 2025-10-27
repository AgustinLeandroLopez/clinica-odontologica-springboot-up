package dao;

import entity.Odontologo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDAOH2 implements iDao<Odontologo>{
    //Constantes
    private static final String SQL_SELECT_ONE=" SELECT * FROM ODONTOLOGOS WHERE ID=?";
    private static final String SQL_INSERT_ONE="INSERT INTO ODONTOLOGOS (NOMBRE,APELLIDO, MATRICULA) VALUES (?,?,?)";
    private static final String SQL_SELECT_ALL=" SELECT * FROM ODONTOLOGOS";
    private static final String SQL_DELETE_ONE ="DELETE FROM ODONTOLOGOS WHERE ID=?";
    private static final String SQL_UPDATE_ONE = "UPDATE ODONTOLOGOS SET NOMBRE=?, APELLIDO=? WHERE ID=?";

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Connection connection=null;
        try{
            connection=BD.getConnection();

            Statement statement = connection.createStatement();
            PreparedStatement ps_insert_one = connection.prepareStatement(SQL_INSERT_ONE, Statement.RETURN_GENERATED_KEYS);

            ps_insert_one.setString(1, odontologo.getNombre());
            ps_insert_one.setString(2, odontologo.getApellido());
            ps_insert_one.setInt(3, odontologo.getMatricula());
            ps_insert_one.executeUpdate();

            // tomo ID autogenerado
            try (ResultSet rs = ps_insert_one.getGeneratedKeys()) {
                if (rs.next()) {
                    odontologo.setId(rs.getInt(1));
                }
            }

            System.out.println("Odontologo creado");
            return odontologo;

        } catch (Exception e) {
            e.printStackTrace();
            return odontologo;
        }
    }

    @Override
    public Odontologo buscar(Integer id) {
        Connection connection=null;
        Odontologo odontologo = null;
        try{
            connection=BD.getConnection();
            //statement mundo java a sql
            Statement statement= connection.createStatement();
            PreparedStatement ps_select_one= connection.prepareStatement(SQL_SELECT_ONE);
            ps_select_one.setInt(1,id);
            //ResultSet mundo bdd a java
            ResultSet rs= ps_select_one.executeQuery();
            while(rs.next()){
                odontologo = new Odontologo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
            }
        }catch (Exception e){
            e.getMessage();
        }
        System.out.println("Odontolo encontrado");
        return odontologo;
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
                System.out.println("Odontologo eliminado correctamente (ID=" + id + ")");
            }else{
                System.out.println("No se encontró odontologo con ID=" + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        Connection connection=null;
        try{
            connection=BD.getConnection();

            Statement statement = connection.createStatement();
            PreparedStatement ps_update_one = connection.prepareStatement(SQL_UPDATE_ONE, Statement.RETURN_GENERATED_KEYS);

            ps_update_one.setString(1, odontologo.getNombre());
            ps_update_one.setString(2, odontologo.getApellido());;
            ps_update_one.setInt(3, odontologo.getId());
            ps_update_one.executeUpdate();

            System.out.println("Odontologo Actualizado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Odontologo> buscarTodos() {
        Connection connection=null;
        List<Odontologo> lista = new ArrayList<>();
        Odontologo odontologo = null;
        try{
            connection=BD.getConnection();
            //statement mundo java a sql
            Statement statement= connection.createStatement();
            PreparedStatement ps_select_all= connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet rs= ps_select_all.executeQuery();

            while(rs.next()){
                odontologo= new Odontologo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
                lista.add(odontologo);
            }
        }catch (Exception e){
            e.getMessage();
        }
        System.out.println("Lista de odontologos");
        return lista;
    }

    @Override
    public Odontologo buscarGenerico(String parametro) {
        return null;
    }
}
