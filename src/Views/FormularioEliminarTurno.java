package Views;

import Entidades.Medico;
import Entidades.Paciente;
import Entidades.Turno;
import Service.MedicoService;
import Service.PacienteService;
import Service.ServiceException;
import Service.TurnoService;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;

public class FormularioEliminarTurno implements Formulario, DecorarFormulario {
    TurnoService turnoService;
    MedicoService medicoService;
    PacienteService pacienteService;
    JPanel formularioEliminarTurno;
    JButton jButtonSend;
    JButton jButtonExit;
    JComboBox<String> jComboBoxDniMedico;
    JComboBox<String> jComboBoxDniPaciente;
    JLabel jLabelDniMedico;
    JLabel jLabelDniPaciente;
    PanelManager panel;
    FormularioAdmin formularioAdmin;
    FormularioEliminarTurnoFecha formularioEliminarTurnoFecha;
    ArrayList<Turno> turnos;
    public FormularioEliminarTurno(PanelManager panel) throws ServiceException, ParseException {
        this.panel=panel;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        turnoService = new TurnoService();
        formularioEliminarTurno = new JPanel();
        formularioEliminarTurno.setLayout(new GridLayout(3,2));
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
        jComboBoxDniPaciente = new JComboBox<>();
        jComboBoxDniMedico = new JComboBox<>();
        jLabelDniPaciente = new JLabel("Dni paciente");
        jLabelDniMedico = new JLabel("Dni medico");
        ArrayList<Medico> medicos = fillarrayMedicos();
        for (Medico m : medicos) {
            jComboBoxDniMedico.addItem(m.getDniMedico() + "-" + m.getNombre() + " " + m.getApellido());
        }
        ArrayList<Paciente> pacientes = fillarrayPacientes();
        jComboBoxDniPaciente = new JComboBox<>();
        for (Paciente p : pacientes) {
            jComboBoxDniPaciente.addItem(p.getDniPaciente() + "-" + p.getNombre() + " " + p.getApellido());
        }
    }

    private ArrayList<Paciente> fillarrayPacientes() throws ServiceException {
        ArrayList<Paciente> pacientes;
        pacienteService = new PacienteService();
        pacientes = pacienteService.buscarTodos();
        return pacientes;
    }

    private ArrayList<Medico> fillarrayMedicos() throws ServiceException {
        ArrayList<Medico> medicos;
        medicoService = new MedicoService();
        medicos = medicoService.buscarTodos();
        return medicos;
    }

    @Override
    public void agregarFormulario() {
        formularioEliminarTurno.add(jLabelDniPaciente);
        formularioEliminarTurno.add(jComboBoxDniPaciente);
        formularioEliminarTurno.add(jLabelDniMedico);
        formularioEliminarTurno.add(jComboBoxDniMedico);
        formularioEliminarTurno.add(jButtonExit);
        formularioEliminarTurno.add(jButtonSend);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonExit.addActionListener(e -> {
            try {
                formularioAdmin = new FormularioAdmin(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioAdmin.getFormulario());
        });
        jButtonSend.addActionListener(e -> {
            try {
                Turno turno = new Turno();
                turno.setPaciente(Integer.parseInt(jComboBoxDniPaciente.getSelectedItem().toString().split("-")[0]));
                turno.setMedico(Integer.parseInt(jComboBoxDniMedico.getSelectedItem().toString().split("-")[0]));
                turnos= new ArrayList<>(turnoService.buscarTurnosPorPacienteYMedico(turno));
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null,"Error al seleccionar el turno");
            }
            if (turnos.isEmpty()){
                JOptionPane.showMessageDialog(null,"No hay turnos para eliminar");
            }else{
                try {
                    formularioEliminarTurnoFecha = new FormularioEliminarTurnoFecha(panel,turnos);
                } catch (ServiceException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
                panel.mostrar(formularioEliminarTurnoFecha.getFormulario());
            }
        });
    }

    public JPanel getFormulario() {
        return formularioEliminarTurno;
    }

    @Override
    public void decorar() {
        formularioEliminarTurno.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioEliminarTurno.setBackground(Color.lightGray);
        formularioEliminarTurno.setPreferredSize(new Dimension(450, 120));
        formularioEliminarTurno.setOpaque(true);
    }
}
