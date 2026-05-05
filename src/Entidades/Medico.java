package Entidades;

public class Medico extends Persona{
    private int dniMedico;
    private String especialidad;

    public Medico() {
        super();
    }

    public Medico(int dniMedico, String nombre, String apellido, String especialidad) {
        super(nombre, apellido);
        this.dniMedico = dniMedico;
        this.especialidad = especialidad;
    }
    public Medico(int dniMedico) {
        super(); // Llama al constructor vacío de Persona
        this.dniMedico = dniMedico;
        this.especialidad = "";
    }

    public int getDniMedico() {
        return dniMedico;
    }

    public void setDniMedico(int dniMedico) {
        this.dniMedico = dniMedico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "dniMedico=" + dniMedico +
                ", especialidad='" + especialidad + '\'' +
                "} " + super.toString();
    }
}