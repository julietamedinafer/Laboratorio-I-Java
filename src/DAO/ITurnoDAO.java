package DAO;

import Entidades.Turno;

import java.util.ArrayList;

public interface ITurnoDAO {
    void guardar(Turno turno) throws DAOException;
    void eliminar(Turno turno) throws DAOException;

    void modificar(Turno turno) throws DAOException;
    Turno buscar(Turno id) throws DAOException;
    ArrayList<Turno> buscarTodos() throws DAOException;
}
