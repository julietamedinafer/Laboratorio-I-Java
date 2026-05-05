package Views;

import Entidades.Turno;
import Service.ServiceException;
import Service.TurnoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

public class FormularioModificarTurnoFecha implements Formulario, DecorarFormulario {
    JPanel formularioModificarTurnoFecha;
    ArrayList<Turno> turnos;
    JButton jButtonSend;
    JButton jButtonExit;
    JLabel jLabelFecha;
    JComboBox jComboBoxFecha;
    TurnoService turnoService;
    PanelManager panel;
    Turno turno;
    FormularioModificarTurnoFinal formularioModificarTurnoFinal;
    public FormularioModificarTurnoFecha(PanelManager panel, ArrayList<Turno> turnos) throws ServiceException, ParseException {
        this.panel=panel;
        this.turnos=turnos;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        formularioModificarTurnoFecha = new JPanel();
        formularioModificarTurnoFecha.setLayout(new GridLayout(2,2));
        jLabelFecha = new JLabel("Fecha");
        jComboBoxFecha = new JComboBox();
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
        for (Turno turno : turnos) {
            jComboBoxFecha.addItem(turno.getFecha());
        }
    }

    @Override
    public void agregarFormulario() {
        formularioModificarTurnoFecha.add(jLabelFecha);
        formularioModificarTurnoFecha.add(jComboBoxFecha);
        formularioModificarTurnoFecha.add(jButtonExit);
        formularioModificarTurnoFecha.add(jButtonSend);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioAdmin formularioAdmin = null;
                try {
                    formularioAdmin = new FormularioAdmin(panel);
                } catch (ServiceException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
                panel.mostrar(formularioAdmin.getFormulario());
            }
        });
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnoService = new TurnoService();
                turno = turnos.get(0);
                turno.setFecha(jComboBoxFecha.getSelectedItem().toString());
                try {
                    formularioModificarTurnoFinal = new FormularioModificarTurnoFinal(panel,turno);
                } catch (ServiceException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
                panel.mostrar(formularioModificarTurnoFinal.getFormulario());
            }
        });
    }

    public JPanel getFormulario() {
        return formularioModificarTurnoFecha;
    }

    @Override
    public void decorar() {
        formularioModificarTurnoFecha.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioModificarTurnoFecha.setBackground(Color.lightGray);
        formularioModificarTurnoFecha.setPreferredSize(new Dimension(220, 80));
        formularioModificarTurnoFecha.setOpaque(true);
    }
}
