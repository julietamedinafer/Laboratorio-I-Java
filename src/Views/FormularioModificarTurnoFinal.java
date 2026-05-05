package Views;

import Entidades.Turno;
import Service.ServiceException;
import Service.TurnoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class FormularioModificarTurnoFinal implements Formulario, DecorarFormulario {
    Turno turno;
    JPanel formularioModificarTurnoFinal;
    JLabel jLabelCosto;
    JTextField jTextFieldCosto;
    JButton jButtonSend;
    JButton jButtonExit;
    PanelManager panel;
    FormularioAdmin formularioAdmin;
    FormularioModificarTurno formularioModificarTurno;
    TurnoService turnoService;
    public FormularioModificarTurnoFinal(PanelManager panel, Turno turno) throws ServiceException, ParseException {
        this.panel=panel;
        this.turno=turno;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        formularioModificarTurnoFinal = new JPanel();
        formularioModificarTurnoFinal.setLayout(new GridLayout(2,2));
        jLabelCosto = new JLabel("Costo");
        jTextFieldCosto = new JTextField();
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarFormulario() {
        formularioModificarTurnoFinal.add(jLabelCosto);
        formularioModificarTurnoFinal.add(jTextFieldCosto);
        formularioModificarTurnoFinal.add(jButtonExit);
        formularioModificarTurnoFinal.add(jButtonSend);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formularioModificarTurno = new FormularioModificarTurno(panel);
                } catch (ServiceException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
                panel.mostrar(formularioModificarTurno.getFormulario());
            }
        });
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnoService = new TurnoService();
                try {
                    turno.setCosto(Double.parseDouble(jTextFieldCosto.getText()));
                    turnoService.modificar(turno);
                    formularioAdmin = new FormularioAdmin(panel);
                    panel.mostrar(formularioAdmin.getFormulario());
                    JOptionPane.showMessageDialog(null, "Se modifico el turno");
                } catch (Exception exception) {
                    if (exception instanceof NumberFormatException) {
                        JOptionPane.showMessageDialog(null, "Ingrese un valor numérico válido para el costo");
                    } else {
                        exception.printStackTrace();
                    }
                }
            }
        });
    }

    public JPanel getFormulario() {
        return formularioModificarTurnoFinal;
    }

    @Override
    public void decorar() {
        formularioModificarTurnoFinal.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioModificarTurnoFinal.setBackground(Color.lightGray);
        formularioModificarTurnoFinal.setPreferredSize(new Dimension(220, 80));
        formularioModificarTurnoFinal.setOpaque(true);
    }
}
