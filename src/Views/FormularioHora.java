package Views;

import Entidades.Medico;
import Entidades.Paciente;
import Entidades.Turno;
import Service.ServiceException;
import Service.TurnoService;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;

public class FormularioHora extends JPanel implements Formulario, DecorarFormulario{
    FormularioAgregarTurno formularioAgregarTurno;
    FormularioAdmin formularioAdmin;
    JPanel formularioHora;
    TurnoService turnoService;
    JLabel jLabelHora, jLabelDniMedico, jLabelDniPaciente, jLabelFecha, jLabelCosto;
    JComboBox<String> jComboBoxHora;
    JTextField jTextFieldDniMedico, jTextFieldDniPaciente, jTextFieldFecha, jTextFieldCosto;
    JButton jButtonSend;
    JButton jButtonExit;
    PanelManager panel;
    Turno turno;
    public FormularioHora (PanelManager panel,Turno turno) throws ServiceException, ParseException {
        this.panel=panel;
        this.turno=turno;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        turnoService = new TurnoService();
        formularioHora = new JPanel();
        formularioHora.setLayout(new GridLayout(6,2));
        
        jLabelDniMedico = new JLabel("Dnimedico");
        jTextFieldDniMedico = new JTextField(String.valueOf(turno.getMedico().getDniMedico()));
        jLabelDniPaciente = new JLabel("Dnipaciente");
        jTextFieldDniPaciente = new JTextField(String.valueOf(turno.getPaciente().getDniPaciente()));
        jLabelFecha = new JLabel("fecha");
        jTextFieldFecha = new JTextField(turno.getFecha());
        jLabelCosto = new JLabel("costo");
        jTextFieldCosto = new JTextField(String.valueOf(turno.getCosto()));
        jLabelHora = new JLabel("hora");
        jComboBoxHora = new JComboBox<>();

        ArrayList<String> hs = turno.fillarrayHoras();
        if (hs.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay horarios disponibles para este turno.");
        }
        for (String hora : hs) {
            jComboBoxHora.addItem(hora);
        }
/*
        try {
            Medico medico = turno.getMedico();
            Paciente paciente = turno.getPaciente();

            if (medico != null && paciente != null) {
                int dniMedico = medico.getDniMedico();
                int dniPaciente = paciente.getDniPaciente();

                if (dniMedico > 0 && dniPaciente > 0) {
                    jTextFieldDniMedico = new JTextField(String.valueOf(dniMedico));
                    jTextFieldDniPaciente = new JTextField(String.valueOf(dniPaciente));
                } else {
                    throw new IllegalArgumentException("DNI inválido.");
                }
            } else {
                throw new NullPointerException("Medico o Paciente no inicializado correctamente.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los DNI: " + ex.getMessage());
            jTextFieldDniMedico = new JTextField("0");
            jTextFieldDniPaciente = new JTextField("0");
        }
*/
        jTextFieldDniMedico.setEnabled(false);
        jTextFieldDniPaciente.setEnabled(false);
        jTextFieldFecha.setEnabled(false);
        jTextFieldCosto.setEnabled(false);

        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarFormulario() {
        formularioHora.add(jLabelDniMedico);
        formularioHora.add(jTextFieldDniMedico);
        formularioHora.add(jLabelDniPaciente);
        formularioHora.add(jTextFieldDniPaciente);
        formularioHora.add(jLabelFecha);
        formularioHora.add(jTextFieldFecha);
        formularioHora.add(jLabelCosto);
        formularioHora.add(jTextFieldCosto);
        formularioHora.add(jLabelHora);
        formularioHora.add(jComboBoxHora);
        formularioHora.add(jButtonExit);
        formularioHora.add(jButtonSend);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonSend.addActionListener(e -> {
            try {
                // Ajustar la fecha y hora en el objeto Turno
                turno.setFecha(turno.getFecha() + " " + jComboBoxHora.getSelectedItem().toString());
                turnoService.guardar(turno);
                JOptionPane.showMessageDialog(null, "Se guardó correctamente");
                FormularioAdmin formularioAdmin = new FormularioAdmin(panel);
                panel.mostrar(formularioAdmin.getFormulario());
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null, "Error en el servicio: " + ex.getMessage());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        jButtonExit.addActionListener(e -> {
            try {
                FormularioAgregarTurno formularioAgregarTurno = new FormularioAgregarTurno(panel);
                panel.mostrar(formularioAgregarTurno.getFormulario());
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public DefaultComboBoxModel<String> model(ArrayList<String> horarios) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addAll(horarios); // Agrega todos los elementos del array a la lista
        return model;
    }

    public JPanel getFormulario() {
        return formularioHora;
    }

    @Override
    public void decorar() {
        formularioHora.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioHora.setBackground(Color.lightGray);
        formularioHora.setPreferredSize(new Dimension(450, 200));
        formularioHora.setOpaque(true);
    }
}
