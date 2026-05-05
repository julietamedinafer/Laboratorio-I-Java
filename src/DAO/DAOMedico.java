package DAO;

import Entidades.Medico;

import java.sql.*;
import java.util.ArrayList;

public class DAOMedico implements IMedicoDAO{
    private final String DB_JDBC_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:jdbch2./test";
    private final String DB_USER = "sa";
    private final String DB_PASSWORD = "sa";

    @Override
    public void guardar(Medico medico) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("INSERT INTO Medico (dniMedico, nombre, apellido, especialidad) VALUES ( ?,?,?,?)");
            preparedStatement.setInt(1, medico.getDniMedico());
            preparedStatement.setString(2, medico.getNombre());
            preparedStatement.setString(3, medico.getApellido());
            preparedStatement.setString(4, medico.getEspecialidad());
            int res=preparedStatement.executeUpdate();
            System.out.println("Se agregaron " + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new DAOException("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    @Override
    public Medico buscar(int dniMedico) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        Medico medico=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Medico WHERE dniMedico= ?");
            preparedStatement.setInt(1, dniMedico);
            ResultSet resultSet =preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Medico(resultSet.getInt("dniMedico"), resultSet.getString("nombre"),
                        resultSet.getString("apellido"), resultSet.getString("especialidad"));
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            throw  new DAOException(e.getMessage());
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new DAOException("Error al cerrar conexión: " + e.getMessage());
            }
        }
        return medico;
    }

    @Override
    public void modificar(Medico medico) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("UPDATE Medico SET nombre= ?, apellido= ?, especialidad= ? WHERE dniMedico= ?");
            preparedStatement.setString(1, medico.getNombre());
            preparedStatement.setString(2, medico.getApellido());
            preparedStatement.setString(3, medico.getEspecialidad());
            preparedStatement.setLong(4,medico.getDniMedico());
            int res=preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new DAOException("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    
    @Override
    public void eliminar(int dniMedico) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("DELETE FROM Medico WHERE dniMedico=" + dniMedico);
            int res=preparedStatement.executeUpdate();
            if (res > 0){
                System.out.println("Se eliminó " + res);
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new DAOException("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }




    @Override
    public ArrayList<Medico> buscarTodos() throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<Medico> datos=new ArrayList<>();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Medico");
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                Medico medico = new Medico(
                        resultSet.getInt("dniMedico"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("especialidad"));
                datos.add(medico);
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new DAOException("Error al cerrar conexión: " + e.getMessage());
            }
        }
        return datos;
    }
}
