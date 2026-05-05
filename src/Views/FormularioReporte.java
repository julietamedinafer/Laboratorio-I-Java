package Views;

import Entidades.Medico;
import Entidades.Turno;
import Service.MedicoService;
import Service.ServiceException;
import Service.TurnoService;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;

public class FormularioReporte extends JPanel implements Formulario, DecorarFormulario {
    MedicoService medicoService;
    ArrayList<Turno> listaTurnos;
    FormularioReporteFinal formularioReporteFinal;
    FormularioAdmin formularioAdmin;
    JPanel formularioReporte;
    PanelManager panel;
    JFormattedTextField jTextFieldFecha1;
    JLabel jLabelFecha1;
    JFormattedTextField jTextFieldFecha2;
    JLabel jLabelFecha2;
    JComboBox<String> jComboBoxDniMedico;
    JLabel jLabelDniMedico;
    JButton jButtonSend;
    JButton jButtonExit;
    TurnoService turnoService;

    public FormularioReporte(PanelManager panel) throws ServiceException, ParseException {
        this.panel=panel;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void decorar() {
        formularioReporte.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioReporte.setBackground(Color.lightGray);
        formularioReporte.setPreferredSize(new Dimension(450, 160));
        formularioReporte.setOpaque(true);
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        formularioReporte = new JPanel();
        jButtonSend = new JButton("Buscar");
        jLabelDniMedico = new JLabel("Dni");
        jTextFieldFecha1 = new JFormattedTextField(createMaskFormatter());
        jTextFieldFecha2 = new JFormattedTextField(createMaskFormatter());
        jLabelFecha1 = new JLabel("Fecha 1");
        jLabelFecha2 = new JLabel("Fecha 2");
        jButtonExit = new JButton("Salir");
        jComboBoxDniMedico = new JComboBox<>();
        ArrayList<Medico> medicos = fillarrayMedicos();
        for (Medico m : medicos){
            jComboBoxDniMedico.addItem(m.getDniMedico() + "-" + m.getNombre() + " " + m.getApellido());
        }
        formularioReporte.setLayout(new GridLayout(4,2));
    }

    private ArrayList<Medico> fillarrayMedicos() throws ServiceException {
        ArrayList<Medico> medicos;
        medicoService = new MedicoService();
        medicos = medicoService.buscarTodos();
        return medicos;
    }

    private MaskFormatter createMaskFormatter() {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("####/##/##");
            formatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }

    @Override
    public void agregarFormulario() {
        formularioReporte.add(jLabelDniMedico);
        formularioReporte.add(jComboBoxDniMedico);
        formularioReporte.add(jLabelFecha1);
        formularioReporte.add(jTextFieldFecha1);
        formularioReporte.add(jLabelFecha2);
        formularioReporte.add(jTextFieldFecha2);
        formularioReporte.add(jButtonExit);
        formularioReporte.add(jButtonSend);
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
                turnoService = new TurnoService();
                listaTurnos = new ArrayList<>();
                ArrayList<Turno> listaTurnos = turnoService.CobrosMedico(jTextFieldFecha1.getText(),jTextFieldFecha2.getText(),Integer.parseInt(jComboBoxDniMedico.getSelectedItem().toString().split("-")[0]));
                formularioReporteFinal = new FormularioReporteFinal(panel,listaTurnos,jTextFieldFecha1.getText(),jTextFieldFecha2.getText());
                panel.mostrar(formularioReporteFinal.getFormulario());
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "No hay turnos");
                exception.printStackTrace();
            }
        });
    }

    @Override
    public JPanel getFormulario() {
        return formularioReporte;
    }
}
