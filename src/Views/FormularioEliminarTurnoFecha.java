package Views;

import Entidades.Turno;
import Service.ServiceException;
import Service.TurnoService;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;

public class FormularioEliminarTurnoFecha implements Formulario, DecorarFormulario {
    JPanel formularioEliminarTurnoFecha;
    ArrayList<Turno> turnos;
    JButton jButtonSend;
    JButton jButtonExit;
    JLabel jLabelFecha;
    JComboBox<String> jComboBoxFecha;
    TurnoService turnoService;
    PanelManager panel;
    Turno turno;
    FormularioAdmin formularioAdmin;
    public FormularioEliminarTurnoFecha(PanelManager panel, ArrayList<Turno> turnos) throws ServiceException, ParseException {
        this.panel=panel;
        this.turnos=turnos;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        formularioEliminarTurnoFecha = new JPanel();
        formularioEliminarTurnoFecha.setLayout(new GridLayout(2,2));
        jLabelFecha = new JLabel("Fecha");
        jComboBoxFecha = new JComboBox<>();
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
        for (Turno turno : turnos) {
            jComboBoxFecha.addItem(turno.getFecha());
        }
    }

    @Override
    public void agregarFormulario() {
        formularioEliminarTurnoFecha.add(jLabelFecha);
        formularioEliminarTurnoFecha.add(jComboBoxFecha);
        formularioEliminarTurnoFecha.add(jButtonExit);
        formularioEliminarTurnoFecha.add(jButtonSend);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonExit.addActionListener(e -> {
            FormularioAdmin formularioAdmin;
            try {
                formularioAdmin = new FormularioAdmin(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioAdmin.getFormulario());
        });
        jButtonSend.addActionListener(e -> {
            turnoService = new TurnoService();
            //quiero que turno sea el turno sea el primer nodo de turnos y que tenga la fecha que selecciono en el combobox
            turno = turnos.get(0);
            turno.setFecha(jComboBoxFecha.getSelectedItem().toString());
            try {
                turnoService.eliminar(turno);
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null,"Error al eliminar turno ");
                throw new RuntimeException(ex);
            }
            try {
                formularioAdmin = new FormularioAdmin(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioAdmin.getFormulario());
            JOptionPane.showMessageDialog(null,"Turno eliminado");
        });
    }

    public JPanel getFormulario() {
        return formularioEliminarTurnoFecha;
    }

    @Override
    public void decorar() {
        formularioEliminarTurnoFecha.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioEliminarTurnoFecha.setBackground(Color.lightGray);
        formularioEliminarTurnoFecha.setPreferredSize(new Dimension(220, 80));
        formularioEliminarTurnoFecha.setOpaque(true);

    }
}
