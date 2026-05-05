package DAO;

import java.sql.*;

public class DAOAdmin {
    public boolean login(String usuario, String contrasena) throws DAOException, SQLException {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:jdbch2./test", "sa", "sa");
            preparedStatement = connection.prepareStatement("SELECT * FROM Admin WHERE usuario=? AND contrasena=?");
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, contrasena);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}

//con login verifico credenciales del usuario.
//si devuelve true es porque hubo ingreso de sesion, caso contrario devuelve false
//daoexecption si hubo un problema con la base de datos
