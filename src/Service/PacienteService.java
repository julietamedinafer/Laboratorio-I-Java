package Service;

import DAO.DAOCreate;
import DAO.DAOPaciente;
import Entidades.Paciente;

import java.sql.SQLException;
import java.util.ArrayList;

public class PacienteService {
    private final DAOPaciente daoPaciente;

    public PacienteService(){
        this.daoPaciente = new DAOPaciente();
    }
    public boolean existePaciente(int dniPaciente) throws ServiceException {
        try {
            return DAOCreate.existePaciente(dniPaciente);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServiceException("Error al verificar el DNI del paciente");
        }
    }
    public void guardar(Paciente paciente) throws ServiceException {
        try {
            daoPaciente.guardar(paciente);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void modificar(Paciente paciente) throws ServiceException {
        try {
            daoPaciente.modificar(paciente);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void eliminar(Paciente paciente) throws ServiceException {
        try {
            daoPaciente.eliminar(paciente.getDniPaciente());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public Paciente buscar(Paciente paciente) throws ServiceException {
        try {
            return daoPaciente.buscar(paciente.getDniPaciente());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Paciente> buscarTodos() throws ServiceException {
        try {
            return daoPaciente.buscarTodos();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
