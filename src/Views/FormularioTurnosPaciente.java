package Views;

import Entidades.Turno;
import Service.ServiceException;
import Service.TurnoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;

public class FormularioTurnosPaciente implements Formulario, DecorarFormulario{
    TurnoService turnoService;
    DefaultTableModel model;
    JPanel formularioTurnosPaciente;
    PanelManager panel;
    ArrayList<Turno> turnos;
    JButton jButtonVolver;
    public FormularioTurnosPaciente(PanelManager panel, ArrayList<Turno> turnos) {
        this.panel=panel;
        this.turnos=turnos;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    public void creadorFormulario() {
        formularioTurnosPaciente = new JPanel();
        turnoService = new TurnoService();
        formularioTurnosPaciente.setLayout(new BoxLayout(formularioTurnosPaciente, BoxLayout.Y_AXIS));
        model = new DefaultTableModel();
        model.addColumn("dni medico");
        model.addColumn("turno");
        model.addColumn("costo");
        for (Turno turno : this.turnos){
            model.addRow(new Object[]{turno.getMedico().getDniMedico(), turno.getFecha(), turno.getCosto()});
        }
        formularioTurnosPaciente.add(new JScrollPane(new JTable(model)));
        jButtonVolver = new JButton("Atrás");
        jButtonVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    @Override
    public void agregarFormulario() {
        formularioTurnosPaciente.add(jButtonVolver);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonVolver.addActionListener(e -> {
            FormularioUsuarioPaciente formularioUsuarioPaciente;
            try {
                formularioUsuarioPaciente = new FormularioUsuarioPaciente(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioUsuarioPaciente.getFormulario());
        });
    }

    public JPanel getFormulario() {
        return formularioTurnosPaciente;
    }

    @Override
    public void decorar() {
        formularioTurnosPaciente.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioTurnosPaciente.setBackground(Color.lightGray);
        formularioTurnosPaciente.setPreferredSize(new Dimension(450, 160));
        formularioTurnosPaciente.setOpaque(true);
    }
}
