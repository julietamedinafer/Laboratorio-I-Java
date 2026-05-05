package DAO;

import Entidades.Paciente;

import java.util.ArrayList;

public interface IPacienteDAO {
    void guardar(Paciente paciente) throws DAOException;
    void eliminar(int dniPaciente) throws DAOException;
    void modificar(Paciente paciente) throws DAOException;
    Paciente buscar(int dniPaciente) throws DAOException;
    ArrayList<Paciente> buscarTodos() throws DAOException;
}
