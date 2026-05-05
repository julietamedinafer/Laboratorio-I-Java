package Views;

import DAO.DAOException;
import Service.AdminService;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class FormularioLoginAdmin implements Formulario, DecorarFormulario {
    JPanel formularioLoginAdmin;
    PanelManager panel;
    JButton jButtonLoginAdmin;
    JButton jButtonExit;
    JTextField jTextFieldUser;
    JPasswordField jPasswordField;
    JLabel jLabelUser;
    JLabel jLabelPassword;
    AdminService adminService;
    public FormularioLoginAdmin(PanelManager panel) throws ServiceException, ParseException {
        this.panel=panel;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario(){
        formularioLoginAdmin = new JPanel();
        formularioLoginAdmin.setLayout(new GridLayout(3,2));
        jButtonLoginAdmin = new JButton("Ingresar");
        jButtonExit = new JButton("Salir");
        jTextFieldUser = new JTextField();
        jPasswordField = new JPasswordField();
        jLabelUser = new JLabel("Usuario:");
        jLabelPassword = new JLabel("Contraseña:");
    }

    @Override
    public void agregarFormulario() {
        formularioLoginAdmin.add(jLabelUser);
        formularioLoginAdmin.add(jTextFieldUser);
        formularioLoginAdmin.add(jLabelPassword);
        formularioLoginAdmin.add(jPasswordField);
        formularioLoginAdmin.add(jButtonExit);
        formularioLoginAdmin.add(jButtonLoginAdmin);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioSeleccionUsuario formularioSeleccionUsuario = null;
                try {
                    formularioSeleccionUsuario = new FormularioSeleccionUsuario(panel);
                } catch (ServiceException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
                panel.mostrar(formularioSeleccionUsuario.getFormulario());
            }
        });
        jButtonLoginAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminService = new AdminService();
                String user = jTextFieldUser.getText();
                String password = String.valueOf(jPasswordField.getPassword());
                try {
                    if(adminService.login(user,password)){
                        FormularioAdmin formularioAdmin = new FormularioAdmin(panel);
                        panel.mostrar(formularioAdmin.getFormulario());
                    }else{
                        JOptionPane.showMessageDialog(null,"Usuario o contraseña incorrectos");
                    }
                } catch (Exception ex) {  // Catch other exceptions as a fallback
                    JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + ex.getMessage());
                }
            }
        });
    }

    public JPanel getFormulario() {
        return formularioLoginAdmin;
    }

    @Override
    public void decorar() {
        formularioLoginAdmin.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioLoginAdmin.setBackground(Color.lightGray);
        formularioLoginAdmin.setPreferredSize(new Dimension(220, 120));
        formularioLoginAdmin.setOpaque(true);
    }
}
