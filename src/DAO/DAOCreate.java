package DAO;

import java.sql.*;

public class DAOCreate{
    private static final String DB_JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:jdbch2./test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "sa";


    public static void createTablas(){
        createMedico();
        createPaciente();
        createTurno();
        crearAdmin();
    }
    public static void createMedico(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Medico (dniMedico INT PRIMARY KEY, nombre VARCHAR(50), apellido VARCHAR(50), especialidad VARCHAR(50))");
            int res = preparedStatement.executeUpdate();
            System.out.println("Se ha creado la tabla Medico " + res);
        }
        catch (ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                //cierro el preparedStatment para evitar fugas de memoria
                // o problemas de rendimiento
                if (preparedStatement != null) preparedStatement.close();
                //cierro la conexion a la base de datos para liberar recursos del sistema
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createPaciente(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Paciente (dniPaciente INT PRIMARY KEY, nombre VARCHAR(50), apellido VARCHAR(50))");
            int res = preparedStatement.executeUpdate();
            System.out.println("Se ha creado la tabla Paciente " + res);
        }
        catch (ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void createTurno(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Turno (dniMedico INT, dniPaciente INT, fecha VARCHAR(20),costo INT,PRIMARY KEY (dniMedico, dniPaciente, fecha), FOREIGN KEY (dniMedico) REFERENCES Medico(dniMedico), FOREIGN KEY (dniPaciente) REFERENCES Paciente(dniPaciente));\n");
            int res = preparedStatement.executeUpdate();
            System.out.println("Se ha creado la tabla Turno " + res);
        }
        catch (ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //admin solo tiene usuario y contraseña, y los valores que van son admin y admin
    public static void crearAdmin(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Admin (usuario VARCHAR(50) PRIMARY KEY, contrasena VARCHAR(50))");
            int res = preparedStatement.executeUpdate();
            System.out.println("Se ha creado la tabla Admin " + res);

            // Verificar si el usuario 'admin' ya existe
            preparedStatement2 = connection.prepareStatement(
                    "SELECT COUNT(*) FROM Admin WHERE usuario = 'admin'");
            ResultSet resultSet = preparedStatement2.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count == 0) {
                // Insertar usuario admin SI NO EXISTE
                preparedStatement2 = connection.prepareStatement(
                        "INSERT INTO Admin (usuario, contrasena) VALUES ('admin', 'admin')");
                int res2 = preparedStatement2.executeUpdate();
                System.out.println("Se ha insertado el usuario admin " + res2);
            } else {
                // Actualizar la contraseña del usuario admin si ya existe
                preparedStatement2 = connection.prepareStatement(
                        "UPDATE Admin SET contrasena = 'admin' WHERE usuario = 'admin'");
                int res2 = preparedStatement2.executeUpdate();
                System.out.println("Se ha actualizado la contraseña del usuario admin " + res2);
            }

        }catch (ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (preparedStatement2 != null) preparedStatement2.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DB_JDBC_DRIVER);
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    ////////////////////////////////////////////////////
    public static boolean existeMedico(int dniMedico) throws SQLException, ClassNotFoundException {
        String sql = "SELECT 1 FROM Medico WHERE dniMedico = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, dniMedico);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static boolean existePaciente(int dniPaciente) throws SQLException, ClassNotFoundException {
        String sql = "SELECT 1 FROM Paciente WHERE dniPaciente = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, dniPaciente);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    //////////////////////////////////////////////////////////


    public static void dropAllTables() {
        dropTurno();
        dropMedico();
        dropPaciente();
        dropAdmin();
    }

    public static void dropMedico(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS Medico");
            int res = preparedStatement.executeUpdate();
            System.out.println("Se ha eliminado la tabla Medico " + res);
        }
        catch (ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void dropPaciente(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS Paciente");
            int res = preparedStatement.executeUpdate();
            System.out.println("Se ha eliminado la tabla Paciente " + res);
        }
        catch (ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void dropTurno(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS Turno");
            int res = preparedStatement.executeUpdate();
            System.out.println("Se ha eliminado la tabla Turno " + res);
        }
        catch (ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void dropAdmin() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS Admin");
            int res = preparedStatement.executeUpdate();
            System.out.println("Se ha eliminado la tabla Admin " + res);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
