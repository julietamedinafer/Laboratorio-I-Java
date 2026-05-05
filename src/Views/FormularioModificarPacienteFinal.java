package Views;

import Entidades.Paciente;
import Service.PacienteService;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class FormularioModificarPacienteFinal implements Formulario, DecorarFormulario{
    JLabel jLabelNombre;
    JLabel jLabelApellido;
    JTextField jTextFieldNombre;
    JTextField jTextFieldApellido;
    JButton jButtonSend;
    JButton jButtonExit;
    JPanel formularioModificarPacienteFinal;
    PanelManager panel;
    FormularioAdmin formularioAdmin;
    Paciente paciente;

    public FormularioModificarPacienteFinal(PanelManager panel, Paciente paciente) throws ServiceException, ParseException {
        this.panel=panel;
        this.paciente=paciente;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        formularioModificarPacienteFinal = new JPanel();
        formularioModificarPacienteFinal.setLayout(new GridLayout(4,2));
        jLabelNombre = new JLabel("Nombre");
        jLabelApellido = new JLabel("Apellido");
        jTextFieldNombre = new JTextField();
        jTextFieldApellido = new JTextField();
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarFormulario() {
        formularioModificarPacienteFinal.add(jLabelNombre);
        formularioModificarPacienteFinal.add(jTextFieldNombre);
        formularioModificarPacienteFinal.add(jLabelApellido);
        formularioModificarPacienteFinal.add(jTextFieldApellido);
        formularioModificarPacienteFinal.add(jButtonExit);
        formularioModificarPacienteFinal.add(jButtonSend);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                try {
                    paciente.setNombre(jTextFieldNombre.getText());
                    paciente.setApellido(jTextFieldApellido.getText());
                    PacienteService pacienteService = new PacienteService();
                    pacienteService.modificar(paciente);
                    JOptionPane.showMessageDialog(null,"Paciente modificado");
                    formularioAdmin = new FormularioAdmin(panel);
                    panel.mostrar(formularioAdmin.getFormulario());
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null,"Error al modificar un paciente");
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public JPanel getFormulario() {
        return formularioModificarPacienteFinal;
    }

    @Override
    public void decorar() {
        formularioModificarPacienteFinal.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioModificarPacienteFinal.setBackground(Color.lightGray);
        formularioModificarPacienteFinal.setPreferredSize(new Dimension(220, 160));
        formularioModificarPacienteFinal.setOpaque(true);
    }
}
