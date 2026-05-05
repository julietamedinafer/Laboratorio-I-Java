package Entidades;

public class Paciente extends Persona{
    private int dniPaciente;

    public Paciente(int dniPaciente, String nombre, String apellido) {
        super(nombre, apellido);
        this.dniPaciente = dniPaciente;
    }

    public Paciente(int dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public int getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(int dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public Paciente() {

    }
}
