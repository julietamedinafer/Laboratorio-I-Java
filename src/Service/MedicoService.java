package Service;

import DAO.DAOCreate;
import DAO.DAOException;
import DAO.DAOMedico;
import Entidades.Medico;

import java.sql.SQLException;
import java.util.ArrayList;

public class MedicoService {
    private final DAOMedico daoMedico;

    public MedicoService(){
        this.daoMedico = new DAOMedico();
    }
    public boolean existeMedico(int dniMedico) throws ServiceException{
        try {
            return DAOCreate.existeMedico(dniMedico);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServiceException("Error al verificar el DNI del médico");
        }
    }

    public void guardar(Medico medico) throws ServiceException {
        try {
            daoMedico.guardar(medico);
            System.out.println("Médico guardado correctamente: " + medico.getDniMedico());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public void modificar(Medico medico) throws ServiceException {
        try {
            daoMedico.modificar(medico);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public void eliminar(Medico medico) throws ServiceException {
        try {
            daoMedico.eliminar(medico.getDniMedico());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public Medico buscar(Medico medico) throws ServiceException {
        try {
            return daoMedico.buscar(medico.getDniMedico());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public ArrayList<Medico> buscarTodos() throws ServiceException {
        try {
            return daoMedico.buscarTodos();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

}