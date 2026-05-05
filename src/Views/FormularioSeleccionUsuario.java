package Views;

import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class FormularioSeleccionUsuario extends JPanel implements Formulario, DecorarFormulario{
    JPanel formularioSeleccionUsuario;
    FormularioLoginAdmin formularioLoginAdmin;
    FormularioUsuarioMedico formularioUsuarioMedico;
    FormularioUsuarioPaciente formularioUsuarioPaciente;
    PanelManager panel;
    JButton jButtonAdmin;
    JButton jButtonMedico;
    JButton jButtonPaciente;
    JButton jButtonSalir;
    public FormularioSeleccionUsuario(PanelManager panel) throws ServiceException, ParseException {
        this.panel=panel;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario(){
        formularioSeleccionUsuario = new JPanel();
        formularioSeleccionUsuario.setLayout(new GridLayout(4,1));
        jButtonAdmin = new JButton("Administrador");
        jButtonMedico = new JButton("Medico");
        jButtonPaciente = new JButton("Paciente");
        jButtonSalir = new JButton("Salir");
    }

    @Override
    public void agregarFormulario() {
        formularioSeleccionUsuario.add(jButtonAdmin);
        formularioSeleccionUsuario.add(jButtonMedico);
        formularioSeleccionUsuario.add(jButtonPaciente);
        formularioSeleccionUsuario.add(jButtonSalir);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonAdmin.addActionListener(e -> {
            try {
                formularioLoginAdmin = new FormularioLoginAdmin(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioLoginAdmin.getFormulario());
        });
        jButtonMedico.addActionListener(e -> {
            try {
                formularioUsuarioMedico = new FormularioUsuarioMedico(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioUsuarioMedico.getFormulario());
        });
        jButtonPaciente.addActionListener(e -> {
            try {
                formularioUsuarioPaciente = new FormularioUsuarioPaciente(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioUsuarioPaciente.getFormulario());
        });
        jButtonSalir.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(null, "¿Desea salir?", "Atención", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    public JPanel getFormulario() {
        return formularioSeleccionUsuario;
    }

    @Override
    public void decorar() {
        formularioSeleccionUsuario.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioSeleccionUsuario.setBackground(Color.lightGray);
        formularioSeleccionUsuario.setPreferredSize(new Dimension(220, 180));
        formularioSeleccionUsuario.setOpaque(true);
    }
}
