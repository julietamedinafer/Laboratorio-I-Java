package Views;

import Entidades.Turno;
import Service.ServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;

public class FormularioTurnosMedicos implements Formulario, DecorarFormulario{
    DefaultTableModel model;
    JPanel formularioTurnosMedicos;
    PanelManager panel;
    ArrayList<Turno> turnos;
    JButton jButtonVolver;
    public FormularioTurnosMedicos(PanelManager panel, ArrayList<Turno> turnos) {
        this.panel=panel;
        this.turnos=turnos;
        creadorFormularioTurnosMedicos();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    private void creadorFormularioTurnosMedicos() {
        formularioTurnosMedicos = new JPanel();
        formularioTurnosMedicos.setLayout(new BoxLayout(formularioTurnosMedicos, BoxLayout.Y_AXIS));
        model = new DefaultTableModel();
        model.addColumn("dni paciente");
        model.addColumn("hora");
        model.addColumn("costo");
        for (Turno turno : this.turnos) {
            model.addRow(new Object[]{turno.getPaciente().getDniPaciente(), turno.getHora(), turno.getCosto()});
        }
        formularioTurnosMedicos.add(new JScrollPane(new JTable(model)));
        jButtonVolver = new JButton("Atrás");
        jButtonVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {

    }

    @Override
    public void agregarFormulario() {
        formularioTurnosMedicos.add(jButtonVolver);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonVolver.addActionListener(e -> {
            FormularioUsuarioMedico formularioUsuarioMedico;
            try {
                formularioUsuarioMedico = new FormularioUsuarioMedico(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioUsuarioMedico.getFormulario());
        });
    }

    public JPanel getFormulario() {
        return formularioTurnosMedicos;
    }

    @Override
    public void decorar() {
        formularioTurnosMedicos.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioTurnosMedicos.setBackground(Color.lightGray);
        formularioTurnosMedicos.setPreferredSize(new Dimension(450, 160));
        formularioTurnosMedicos.setOpaque(true);
    }
}
