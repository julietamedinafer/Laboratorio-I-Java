package DAO;

import Entidades.Medico;

import java.util.ArrayList;

public interface IMedicoDAO {
    void guardar(Medico medico) throws DAOException;
    void eliminar(int dniMedico) throws DAOException;
    void modificar(Medico medico) throws DAOException;
    Medico buscar(int dniMedico) throws DAOException;
    ArrayList<Medico> buscarTodos() throws DAOException;
}
