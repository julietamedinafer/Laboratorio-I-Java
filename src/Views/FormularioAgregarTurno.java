package Views;

import Entidades.Medico;
import Entidades.Paciente;
import Entidades.Turno;
import Service.MedicoService;
import Service.PacienteService;
import Service.ServiceException;
import Service.TurnoService;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class FormularioAgregarTurno extends JPanel implements Formulario, DecorarFormulario {
    JPanel formularioAgregarTurno;
    FormularioHora formularioHora;
    JLabel jLabelDniMedico;
    JComboBox<Integer> jComboBoxDniMedico;
    JButton jButtonSend;
    JButton jButtonExit;
    JLabel jLabelDniPaciente;
    JComboBox<Integer> jComboBoxDniPaciente;
    JLabel jLabelFecha;
    JFormattedTextField jTextFieldFecha;
    JLabel jLabelCosto;
    JTextField jTextFieldCosto;
    PanelManager panel;
    MedicoService medicoService;
    PacienteService pacienteService;
    Turno turno;
    TurnoService turnoService;

    public FormularioAgregarTurno(PanelManager panel) throws ServiceException, ParseException {
        this.panel = panel;
        this.turnoService = new TurnoService();
        creadorFormulario();
        agregarFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        medicoService = new MedicoService();
        pacienteService = new PacienteService();
        formularioAgregarTurno = new JPanel();
        formularioAgregarTurno.setLayout(new GridLayout(5,2));
        jLabelDniMedico = new JLabel("Dni Medico");
        jComboBoxDniMedico = new JComboBox<>();
        jLabelDniPaciente = new JLabel("Dni Paciente");
        jComboBoxDniPaciente = new JComboBox<>();
        jLabelFecha = new JLabel("Fecha");
        jTextFieldFecha = new JFormattedTextField(createMaskFormatter());
        jLabelCosto = new JLabel("Costo");
        jTextFieldCosto = new JTextField();
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
        jTextFieldFecha.setColumns(10);
        jTextFieldFecha.setFocusLostBehavior(JFormattedTextField.COMMIT);
        jTextFieldFecha.setText("yyyy/MM/dd");

        ArrayList<Medico> medicos = medicoService.buscarTodos();
        if (medicos.isEmpty()){
            JOptionPane.showMessageDialog(null, "No hay médicos disponibles");
        }
        for (Medico m : medicos) {
            jComboBoxDniMedico.addItem(m.getDniMedico());
        }

        ArrayList<Paciente> pacientes = pacienteService.buscarTodos();
        if (pacientes.isEmpty()){
            JOptionPane.showMessageDialog(null, "No hay pacientes disponibles");
        }
        for (Paciente p : pacientes) {
            jComboBoxDniPaciente.addItem(p.getDniPaciente());
        }
    }

    private ArrayList<Paciente> fillarrayPacientes() throws ServiceException {
        ArrayList<Paciente> pacientes;
        pacientes = pacienteService.buscarTodos();
        return pacientes;
    }

    private ArrayList<Medico> fillarrayMedicos() throws ServiceException {
        ArrayList<Medico> medicos;
        medicos = medicoService.buscarTodos();
        return medicos;
    }

    private MaskFormatter createMaskFormatter() {
        MaskFormatter formatter;
        try {
            formatter = new MaskFormatter("####/##/##");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        formatter.setPlaceholderCharacter('_');
        return formatter;
    }

    @Override
    public void agregarFormulario() {
        formularioAgregarTurno.add(jLabelDniMedico);
        formularioAgregarTurno.add(jComboBoxDniMedico);
        formularioAgregarTurno.add(jLabelDniPaciente);
        formularioAgregarTurno.add(jComboBoxDniPaciente);
        formularioAgregarTurno.add(jLabelFecha);
        formularioAgregarTurno.add(jTextFieldFecha);
        formularioAgregarTurno.add(jLabelCosto);
        formularioAgregarTurno.add(jTextFieldCosto);
        formularioAgregarTurno.add(jButtonExit);
        formularioAgregarTurno.add(jButtonSend);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonExit.addActionListener(e -> {
            FormularioAdmin formularioAdmin;
            try {
                formularioAdmin = new FormularioAdmin(panel);
                panel.mostrar(formularioAdmin.getFormulario());
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
        jButtonSend.addActionListener(e -> {
            try {
                turno = new Turno();
                Integer dniMedico = (Integer) jComboBoxDniMedico.getSelectedItem();
                Integer dniPaciente = (Integer) jComboBoxDniPaciente.getSelectedItem();

                if (dniMedico == null || dniPaciente == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione un médico y un paciente válidos.");
                    return;
                }

                turno.setMedico(new Medico(dniMedico)); // Se inicializa correctamente con el DNI.
                turno.setPaciente(new Paciente(dniPaciente)); // Se inicializa correctamente con el DNI.
                turno.setFecha(jTextFieldFecha.getText().trim());

                // Validación del costo
                try {
                    turno.setCosto(Double.parseDouble(jTextFieldCosto.getText().trim()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese un costo válido.");
                    return;
                }

                // Validación de la fecha
                LocalDate currentDate = LocalDate.now();
                LocalDate selectedDate = LocalDate.parse(turno.getFecha(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                if (selectedDate.isAfter(currentDate)) {
                    // Pasar al siguiente formulario
                    formularioHora = new FormularioHora(panel, turno);
                    panel.mostrar(formularioHora.getFormulario());
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese una fecha posterior a la fecha actual.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error en el servicio: " + ex.getMessage());
            }
        });
    }

    public JPanel getFormulario() {
        return formularioAgregarTurno;
    }

    @Override
    public void decorar() {
        formularioAgregarTurno.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioAgregarTurno.setBackground(Color.lightGray);
        formularioAgregarTurno.setPreferredSize(new Dimension(450, 175));
        formularioAgregarTurno.setOpaque(true);
    }
}
