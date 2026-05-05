package Service;

import DAO.DAOCreate;
import DAO.DAOException;
import DAO.DAOTurno;
import Entidades.Turno;

import java.sql.SQLException;
import java.util.ArrayList;

public class TurnoService {
    private final DAOTurno daoTurno;

    public TurnoService(){
        daoTurno = new DAOTurno();
    }

    public void guardar(Turno turno) throws ServiceException {
        try {

            ///////////////VER SI LO SACO////////////////////////
            if (!DAOCreate.existeMedico(turno.getMedico().getDniMedico())) {
                throw new ServiceException("El DNI del médico no existe");
            }
            if (!DAOCreate.existePaciente(turno.getPaciente().getDniPaciente())) {
                throw new ServiceException("El DNI del paciente no existe");
            }
            ////////////////////////////////////

            daoTurno.guardar(turno);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void modificar(Turno turno) throws ServiceException {
        try {
            daoTurno.modificar(turno);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public void eliminar(Turno turno) throws ServiceException {
        try {
            daoTurno.eliminar(turno);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public Turno buscar(Turno turno) throws ServiceException {
        try {
            return daoTurno.buscar(turno);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public ArrayList<Turno> buscarTodos() throws ServiceException {
        try {
            return daoTurno.buscarTodos();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public ArrayList<String> FechaDisponible(String fecha, int dniMedico) throws ServiceException {
        try {
            return daoTurno.FechaDisponible(fecha,dniMedico);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public ArrayList MedicoBuscaTurnos(String fecha, int dniMedico) throws ServiceException {
        try {
            return daoTurno.MedicoBuscaTurnos(fecha,dniMedico);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public ArrayList<Turno> PacienteBuscaTurnos(int dniPaciente) throws ServiceException {
        try {
            return daoTurno.PacienteBuscaTurnos(dniPaciente);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Turno> buscarHorariosPorPaciente(String fecha, int dniPaciente) throws ServiceException {
        try {
            return daoTurno.buscarHorariosPorPaciente(fecha,dniPaciente);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Turno> buscarTurnosPorPacienteYMedico(Turno turno) throws ServiceException {
        try {
            return daoTurno.buscarTurnosPorPacienteYMedico(turno.getPaciente().getDniPaciente(), turno.getMedico().getDniMedico());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Turno>CobrosMedico(String fecha1, String fecha2, int dniMedico) throws ServiceException {
        try {
            return daoTurno.CobrosMedico(fecha1,fecha2,dniMedico);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
