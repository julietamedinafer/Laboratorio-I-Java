package Entidades;

import Service.ServiceException;
import Service.TurnoService;

import java.util.ArrayList;

public class Turno {
    private Paciente paciente;
    private Medico medico;
    private Double costo;
    private String fecha;
    private TurnoService turnoService;

    //crear un turno con toda la informacion inicializada
    public Turno(Medico medico, Paciente paciente, String fecha, Double costo) {
        this.medico = medico;
        this.paciente = paciente;
        this.fecha = fecha;
        this.costo = costo;
    }

    //permite crear un turno y luego modificar sus atributos individualmente
    public Turno() {
        this.medico = new Medico();
        this.paciente = new Paciente();
        this.fecha = "";
        this.costo = 0.0;
    }

    //sirve para cuando tengo info del paciente y el medico pero no sobre fecha o costo
    public Turno(Medico medico, Paciente paciente){
        this.medico = medico;
        this.paciente = paciente;
        this.fecha = "";
        this.costo = 0.0;
    }

    public String getHora(){
        String[] partes = this.fecha.split(" ");
        String hora = partes[1]; // Segunda parte es la hora
        return hora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        if (paciente == null || paciente.getDniPaciente() <= 0) {
            throw new IllegalArgumentException("El paciente no es válido.");
        }
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        if (medico == null || medico.getDniMedico() <= 0) {
            throw new IllegalArgumentException("El médico no es válido.");
        }

        this.medico = medico;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setMedico(int dniMedico) {
        this.medico = new Medico(dniMedico);
    }

    public void setPaciente(int dniPaciente) {
        this.paciente = new Paciente(dniPaciente);
    }

    /*public void setNombreMedico(String nombre) {this.medico.setNombre(nombre);}
    public String getNombreMedico() {return medico.getNombre();}
    public void setApellidoMedico(String apellido) {this.medico.setApellido(apellido);}
    public String getApellidoMedico() {return medico.getApellido();}
*/
    public ArrayList<String> fillarrayHoras() throws ServiceException {
        ArrayList<String> horariosTomados = horariosTomados(this.fecha, this.medico.getDniMedico(), this.paciente.getDniPaciente());
        int horaInicial = 10; // Hora inicial (10:00)
        ArrayList<String> horariosTurnos = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            horariosTurnos.add(String.format("%02d:00", horaInicial));
            horariosTurnos.add(String.format("%02d:30", horaInicial));
            horaInicial++;
        }
        horariosTurnos.removeAll(horariosTomados);
        return horariosTurnos;
    }

    private ArrayList<String> horariosTomados(String fecha, int dniMedico, int dniPaciente) throws ServiceException {
        turnoService = new TurnoService();
        ArrayList<Turno> fechasTomadas;
        ArrayList<String> horariosTomados = new ArrayList<>();
        fechasTomadas = turnoService.MedicoBuscaTurnos(fecha, dniMedico);
        fechasTomadas.addAll(turnoService.buscarHorariosPorPaciente(fecha,dniPaciente));
        for (Turno fechaHora : fechasTomadas) {
            String[] partes = fechaHora.getFecha().split(" ");
            String hora = partes[1]; // Segunda parte es la hora
            horariosTomados.add(hora);
        }
        return horariosTomados;
    }

    private String[] split(String space) {
        return new String[0];
    }
}