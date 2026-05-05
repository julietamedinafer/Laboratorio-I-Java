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

public class FormularioModificarTurno implements Formulario, DecorarFormulario{
    TurnoService turnoService;
    MedicoService medicoService;
    PacienteService pacienteService;
    JPanel formularioModificarTurno;
    JButton jButtonSend;
    JButton jButtonExit;
    JComboBox<String> jComboBoxDniMedico;
    JComboBox<String> jComboBoxDniPaciente;
    JLabel jLabelDniPaciente;
    JLabel jLabelDniMedico;
    PanelManager panel;
    FormularioAdmin formularioAdmin;
    FormularioModificarTurnoFecha formularioModificarTurnoFecha;
    ArrayList<Turno> turnos;
    public FormularioModificarTurno(PanelManager panel) throws ServiceException, ParseException {
        this.panel=panel;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        turnoService = new TurnoService();
        formularioModificarTurno = new JPanel();
        formularioModificarTurno.setLayout(new GridLayout(3,2));
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
        formularioModificarTurno.add(jLabelDniPaciente);
        formularioModificarTurno.add(jComboBoxDniPaciente);
        formularioModificarTurno.add(jLabelDniMedico);
        formularioModificarTurno.add(jComboBoxDniMedico);
        formularioModificarTurno.add(jButtonExit);
        formularioModificarTurno.add(jButtonSend);
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
                JOptionPane.showMessageDialog(null,"No hay turnos para modificar");
            }else{
                try {
                    formularioModificarTurnoFecha = new FormularioModificarTurnoFecha(panel,turnos);
                } catch (ServiceException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
                panel.mostrar(formularioModificarTurnoFecha.getFormulario());
            }
        });
    }

    public JPanel getFormulario() {
        return formularioModificarTurno;
    }

    @Override
    public void decorar() {
        formularioModificarTurno.setBackground(Color.lightGray);
        formularioModificarTurno.setPreferredSize(new Dimension(450, 120));
        formularioModificarTurno.setOpaque(true);
    }
}
