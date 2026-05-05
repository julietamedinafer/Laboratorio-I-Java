package Views;

import Entidades.Medico;
import Service.MedicoService;
import Service.ServiceException;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class FormularioModificarMedicoFinal implements Formulario, DecorarFormulario, SetFormatoJTextField{
    JLabel jLabelNombre;
    JLabel jLabelApellido;
    JLabel jLabelEspecialidad;
    JTextField jTextFieldNombre;
    JTextField jTextFieldApellido;
    JTextField jTextFieldEspecialidad;
    JButton jButtonSend;
    JButton jButtonExit;
    JPanel formularioModificarMedicoFinal;
    PanelManager panel;
    FormularioAdmin formularioAdmin;
    Medico medico;
    public FormularioModificarMedicoFinal(PanelManager panel, Medico medico) throws ServiceException, ParseException {
        this.panel=panel;
        this.medico=medico;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        formularioModificarMedicoFinal = new JPanel();
        formularioModificarMedicoFinal.setLayout(new GridLayout(4,2));
        jLabelNombre = new JLabel("Nombre");
        jLabelApellido = new JLabel("Apellido");
        jLabelEspecialidad = new JLabel("Especialidad");
        jTextFieldNombre = new JTextField();
        jTextFieldApellido = new JTextField();
        jTextFieldEspecialidad = new JTextField();
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarFormulario() {
        formularioModificarMedicoFinal.add(jLabelNombre);
        formularioModificarMedicoFinal.add(jTextFieldNombre);
        formularioModificarMedicoFinal.add(jLabelApellido);
        formularioModificarMedicoFinal.add(jTextFieldApellido);
        formularioModificarMedicoFinal.add(jLabelEspecialidad);
        formularioModificarMedicoFinal.add(jTextFieldEspecialidad);
        formularioModificarMedicoFinal.add(jButtonExit);
        formularioModificarMedicoFinal.add(jButtonSend);
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
                    medico.setNombre(jTextFieldNombre.getText());
                    medico.setApellido(jTextFieldApellido.getText());
                    medico.setEspecialidad(jTextFieldEspecialidad.getText());
                    MedicoService medicoService = new MedicoService();
                    medicoService.modificar(medico);
                    formularioAdmin = new FormularioAdmin(panel);
                    panel.mostrar(formularioAdmin.getFormulario());
                    JOptionPane.showMessageDialog(null,"Se modifico el medico");
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null,"Error al modificar un medico: " + ex.getMessage());
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public JPanel getFormulario() {
        return formularioModificarMedicoFinal;
    }

    @Override
    public void decorar() {
        formularioModificarMedicoFinal.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioModificarMedicoFinal.setBackground(Color.lightGray);
        formularioModificarMedicoFinal.setPreferredSize(new Dimension(220, 160));
        formularioModificarMedicoFinal.setOpaque(true);
    }

    @Override
    public void setFormatoJTextField(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (newText.matches("\\d{0,8}")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
}
