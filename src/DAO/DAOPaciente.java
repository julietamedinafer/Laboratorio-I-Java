package DAO;

import Entidades.Paciente;

import java.sql.*;
import java.util.ArrayList;

public class DAOPaciente implements IPacienteDAO{
    private final String DB_JDBC_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:jdbch2./test";
    private final String DB_USER = "sa";
    private final String DB_PASSWORD = "sa";

    @Override
    public void guardar(Paciente paciente) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("INSERT into Paciente (dniPaciente, nombre, apellido) Values(?,?,?)");
            preparedStatement.setInt(1, paciente.getDniPaciente());
            preparedStatement.setString(2, paciente.getNombre());
            preparedStatement.setString(3, paciente.getApellido());
            int res=preparedStatement.executeUpdate();
            System.out.println("Se agregaron " + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }

    }

    @Override
    public void eliminar(int dniPaciente) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("DELETE FROM Paciente WHERE dniPaciente=" + dniPaciente);
            int res=preparedStatement.executeUpdate();
            if (res > 0){
                System.out.println("Se eliminó " + res);
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
    }

    @Override
    public void modificar(Paciente paciente) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("UPDATE Paciente SET nombre=?, apellido=? WHERE dniPaciente=?");
            preparedStatement.setString(1, paciente.getNombre());
            preparedStatement.setString(2, paciente.getApellido());
            preparedStatement.setInt(3, paciente.getDniPaciente());
            int res=preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
    }

    @Override
    public Paciente buscar(int dniPaciente) throws DAOException {
        Connection connection;
        PreparedStatement preparedStatement;
        Paciente paciente=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Paciente  WHERE dniPaciente=" + dniPaciente);
            ResultSet resultSet =preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Paciente(resultSet.getInt("dniPaciente"), resultSet.getString("nombre"),
                        resultSet.getString("apellido"));
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        return paciente;
    }

    @Override
    public ArrayList<Paciente> buscarTodos() throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<Paciente> datos=new ArrayList<>();
        Paciente paciente=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Paciente");
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                paciente = new Paciente(
                        resultSet.getInt("dniPaciente"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido")
                );
                datos.add(paciente);
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }
}