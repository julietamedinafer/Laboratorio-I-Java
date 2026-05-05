package Views;

import Entidades.Paciente;
import Service.PacienteService;
import Service.ServiceException;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.text.ParseException;

public class FormularioAgregarPaciente extends JPanel implements Formulario, DecorarFormulario, SetFormatoJTextField{
    PacienteService pacienteService;
    JPanel formularioAgregarPaciente;
    JLabel jLabelNombre;
    JTextField  jTextFieldNombre;
    JButton jButtonSend;
    JButton jButtonExit;
    JLabel jLabelApellido;
    JTextField jTextFieldApellido;
    JLabel jLabelDniPaciente;
    JTextField jTextFieldDniPaciente;
    PanelManager panel;

    public FormularioAgregarPaciente (PanelManager panel) throws ServiceException, ParseException {
        this.panel=panel;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }
    @Override
    public void creadorFormulario() {
        pacienteService = new PacienteService();
        formularioAgregarPaciente = new JPanel();
        formularioAgregarPaciente.setLayout(new GridLayout(4,2));
        jLabelNombre = new JLabel("Nombre");
        jLabelApellido = new JLabel("Apellido");
        jLabelDniPaciente = new JLabel("Dni");
        jTextFieldNombre = new JTextField();
        jTextFieldApellido = new JTextField();
        jTextFieldDniPaciente = new JTextField();
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarFormulario() {
        formularioAgregarPaciente.add(jLabelDniPaciente);
        formularioAgregarPaciente.add(jTextFieldDniPaciente);
        formularioAgregarPaciente.add(jLabelNombre);
        formularioAgregarPaciente.add(jTextFieldNombre);
        formularioAgregarPaciente.add(jLabelApellido);
        formularioAgregarPaciente.add(jTextFieldApellido);
        formularioAgregarPaciente.add(jButtonExit);
        formularioAgregarPaciente.add(jButtonSend);
        setFormatoJTextField(jTextFieldDniPaciente);
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
            String dniText = jTextFieldDniPaciente.getText().trim();
            String nombreText = jTextFieldNombre.getText().trim();
            String apellidoText = jTextFieldApellido.getText().trim();

            if (dniText.isEmpty() || nombreText.isEmpty() || apellidoText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos");
            } else {
                try {
                    int dniPaciente = Integer.parseInt(dniText);
                    if (pacienteService.existePaciente(dniPaciente)) {
                        JOptionPane.showMessageDialog(null, "El DNI del paciente ya existe");
                    }else {
                        Paciente paciente = new Paciente();
                        paciente.setDniPaciente(dniPaciente);
                        paciente.setNombre(jTextFieldNombre.getText());
                        paciente.setApellido(jTextFieldApellido.getText());
                        pacienteService.guardar(paciente);
                        JOptionPane.showMessageDialog(null, "Se guardo correctamente");
                    }
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null, "Paciente existente");
                    throw new RuntimeException(ex);
                }
                FormularioAdmin formularioAdmin;
                try {
                    formularioAdmin = new FormularioAdmin(panel);
                } catch (ServiceException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
                panel.mostrar(formularioAdmin.getFormulario());
            }
        });
    }

    public JPanel getFormulario() {
        return formularioAgregarPaciente;
    }

    @Override
    public void decorar() {
        formularioAgregarPaciente.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioAgregarPaciente.setBackground(Color.lightGray);
        formularioAgregarPaciente.setPreferredSize(new Dimension(270, 175));
        formularioAgregarPaciente.setOpaque(true);
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
