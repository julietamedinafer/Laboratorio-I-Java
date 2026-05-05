package DAO;

import Entidades.Turno;

import java.sql.*;
import java.util.ArrayList;

public class DAOTurno implements ITurnoDAO{
    private static final String DB_JDBC_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:jdbch2./test";
    private final String DB_USER = "sa";
    private final String DB_PASSWORD = "sa";

    static {
        try {
            Class.forName(DB_JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(Turno turno) throws DAOException {
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("INSERT into Turno (dniMedico, dniPaciente, fecha, costo) VALUES (?,?,?,?)");
            preparedStatement.setInt(1,turno.getMedico().getDniMedico());
            preparedStatement.setInt(2, turno.getPaciente().getDniPaciente());
            preparedStatement.setString(3, turno.getFecha());
            preparedStatement.setDouble(4, turno.getCosto());
            int res=preparedStatement.executeUpdate();
            System.out.println("Se agregaron " + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
    }

    @Override
    public void eliminar(Turno turno) throws DAOException {
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("DELETE FROM Turno  WHERE dniMedico=? and dniPaciente=? and fecha=?");
            preparedStatement.setInt(1,turno.getMedico().getDniMedico());
            preparedStatement.setInt(2,turno.getPaciente().getDniPaciente());
            preparedStatement.setString(3,turno.getFecha());
            int res=preparedStatement.executeUpdate();
            System.out.println("Se elimino" + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }

    }


    @Override
    public void modificar(Turno turno) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("UPDATE Turno SET costo=? WHERE dniMedico=? and dniPaciente=? and fecha=?");
            preparedStatement.setDouble(1, turno.getCosto());
            preparedStatement.setInt(2,turno.getMedico().getDniMedico());
            preparedStatement.setInt(3, turno.getPaciente().getDniPaciente());
            preparedStatement.setString(4, turno.getFecha());

            int res=preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
    }

    @Override
    public Turno buscar(Turno turno) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Turno  WHERE dniPaciente=? and dniMedico=?");
            preparedStatement.setInt(1,turno.getPaciente().getDniPaciente());
            preparedStatement.setInt(2, turno.getMedico().getDniMedico());
            ResultSet resultSet =preparedStatement.executeQuery();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        return turno;
    }

    @Override
    public ArrayList<Turno> buscarTodos() throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<Turno> datos=new ArrayList<>();
        Turno turno=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Turno");
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                turno = new Turno();
                turno.setMedico(resultSet.getInt("DniMedico"));
                turno.setPaciente(resultSet.getInt("DniPaciente"));
                turno.setFecha(resultSet.getString("Fecha"));
                datos.add(turno);
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }

    //con FechaDisponible muestro los turnos disponibles de un cierto medico (buscarHorariosPorMedico)
    public ArrayList<String> FechaDisponible(String fecha, int dniMedico) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<String> datos=new ArrayList<>();
        String turno=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Turno WHERE SUBSTRING(fecha, 1, 10) = ? AND dniMedico = ?");
            preparedStatement.setString(1, fecha);
            preparedStatement.setInt(2, dniMedico);
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                turno = resultSet.getString("fecha");
                datos.add(turno);
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }

    //con MedicoBuscaTurnos muestro los turnos que tiene registrados un medico en especifico (buscarTurnosMedico)
    public ArrayList<Turno> MedicoBuscaTurnos(String fecha, int dniMedico) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<Turno> datos=new ArrayList<>();
        Turno turno=new Turno();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Turno WHERE SUBSTRING(fecha, 1, 10) = ? AND dniMedico = ? ORDER BY fecha DESC;");
            preparedStatement.setString(1, fecha);
            preparedStatement.setInt(2, dniMedico);
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                turno = new Turno();
                turno.setMedico(resultSet.getInt("DniMedico"));
                turno.setPaciente(resultSet.getInt("DniPaciente"));
                turno.setFecha(resultSet.getString("Fecha"));
                turno.setCosto(resultSet.getDouble("Costo"));
                datos.add(turno);
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }

    //con PacienteBuscaTurnos muestro todos los turnos de un paciente (buscarTurnosPaciente)
    public ArrayList<Turno> PacienteBuscaTurnos(int dniPaciente) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<Turno> datos=new ArrayList<>();
        Turno turno;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Turno WHERE dniPaciente = ? ORDER BY fecha DESC;");
            preparedStatement.setInt(1, dniPaciente);
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                turno = new Turno();
                turno.setMedico(resultSet.getInt("DniMedico"));
                turno.setFecha(resultSet.getString("Fecha"));
                turno.setCosto(resultSet.getDouble("Costo"));
                datos.add(turno);
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }

    //tmb relaciono una fecha con un paciente pero con menos detalles
    public ArrayList<Turno> buscarHorariosPorPaciente(String fecha, int dniPaciente) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<Turno> datos=new ArrayList<>();
        Turno turno;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Turno WHERE SUBSTRING(fecha, 1, 10) = ? AND dniPaciente = ?");
            preparedStatement.setString(1, fecha);
            preparedStatement.setInt(2, dniPaciente);
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                turno = new Turno();
                turno.setFecha(resultSet.getString("Fecha"));
                datos.add(turno);
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }


    //con esto muestro los turnos de un cierto paciente con un cierto medico
    public ArrayList<Turno> buscarTurnosPorPacienteYMedico(int paciente, int medico) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<Turno> datos=new ArrayList<>();
        Turno turno;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Turno WHERE dniPaciente = ? AND dniMedico=? ORDER BY fecha DESC;");
            preparedStatement.setInt(1, paciente);
            preparedStatement.setInt(2, medico);
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                turno = new Turno();
                turno.setFecha(resultSet.getString("fecha"));
                turno.setMedico(resultSet.getInt("DniMedico"));
                turno.setPaciente(resultSet.getInt("DniPaciente"));
                datos.add(turno);
            }
            return datos;
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
    }

    public ArrayList<Turno> CobrosMedico(String fecha1, String fecha2, int dniMedico) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        Turno turno;
        ArrayList<Turno> datos=new ArrayList<>();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Turno WHERE fecha BETWEEN CONCAT(?, ' 00:00') AND CONCAT(?, ' 23:59') AND dniMedico = ? ORDER BY fecha DESC;");
            preparedStatement.setString(1, fecha1);
            preparedStatement.setString(2, fecha2);
            preparedStatement.setInt(3, dniMedico);
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                turno=new Turno();
                turno.setCosto(resultSet.getDouble("costo"));
                turno.setMedico(resultSet.getInt("DniMedico"));
                turno.setPaciente(resultSet.getInt("DniPaciente"));
                turno.setFecha(resultSet.getString("fecha"));
                datos.add(turno);
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }
}
