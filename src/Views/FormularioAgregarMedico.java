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
import java.text.ParseException;

public class FormularioAgregarMedico extends JPanel implements Formulario, DecorarFormulario, SetFormatoJTextField{
    MedicoService medicoService;
    JPanel formularioAgregarMedico;
    JLabel jLabelNombre;
    JTextField  jTextFieldNombre;
    JButton jButtonSend;
    JButton jButtonExit;
    JLabel jLabelApellido;
    JTextField jTextFieldApellido;
    JLabel jLabelDniMedico;
    JTextField jTextFieldDniMedico;
    JLabel jLabelEspecialidad;
    JTextField jTextFieldEspecialidad;
    PanelManager panel;

    public FormularioAgregarMedico(PanelManager panel) throws ServiceException, ParseException {
        this.panel=panel;
        this.medicoService = new MedicoService();
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() {
        medicoService = new MedicoService();
        formularioAgregarMedico = new JPanel();
        formularioAgregarMedico.setLayout(new GridLayout(5,2));
        jLabelNombre = new JLabel("Nombre");
        jLabelApellido = new JLabel("Apellido");
        jLabelDniMedico = new JLabel("Dni");
        jLabelEspecialidad = new JLabel("Especialidad");
        jTextFieldNombre = new JTextField();
        jTextFieldApellido = new JTextField();
        jTextFieldDniMedico = new JTextField();
        jTextFieldEspecialidad = new JTextField();
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarFormulario() {
        formularioAgregarMedico.add(jLabelDniMedico);
        formularioAgregarMedico.add(jTextFieldDniMedico);
        formularioAgregarMedico.add(jLabelNombre);
        formularioAgregarMedico.add(jTextFieldNombre);
        formularioAgregarMedico.add(jLabelApellido);
        formularioAgregarMedico.add(jTextFieldApellido);
        formularioAgregarMedico.add(jLabelEspecialidad);
        formularioAgregarMedico.add(jTextFieldEspecialidad);
        formularioAgregarMedico.add(jButtonExit);
        formularioAgregarMedico.add(jButtonSend);
        setFormatoJTextField(jTextFieldDniMedico);
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
            String dniText = jTextFieldDniMedico.getText().trim();
            String nombreText = jTextFieldNombre.getText().trim();
            String apellidoText = jTextFieldApellido.getText().trim();
            String especialidadText = jTextFieldEspecialidad.getText().trim();

            if (dniText.isEmpty() || nombreText.isEmpty() || apellidoText.isEmpty() || especialidadText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos");
            } else {
                try {
                    int dniMedico = Integer.parseInt(dniText);
                    if (medicoService.existeMedico(dniMedico)) {
                        JOptionPane.showMessageDialog(null, "El DNI del médico ya existe");
                    }else {
                        Medico medico = new Medico(dniMedico, nombreText, apellidoText, especialidadText);

                        medicoService.guardar(medico);
                        JOptionPane.showMessageDialog(null, "Se guardó correctamente");
                    }
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar el médico: " + ex.getMessage());
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
        return formularioAgregarMedico;
    }

    @Override
    public void decorar() {
        formularioAgregarMedico.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioAgregarMedico.setBackground(Color.lightGray);
        formularioAgregarMedico.setPreferredSize(new Dimension(220, 175));
        formularioAgregarMedico.setOpaque(true);
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
