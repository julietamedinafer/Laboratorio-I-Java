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

public class FormularioModificarPaciente implements Formulario, DecorarFormulario, SetFormatoJTextField {
    JPanel formularioModificarPaciente;
    PanelManager panel;
    FormularioAdmin formularioAdmin;
    JLabel jLabelDniPaciente;
    JTextField jTextFieldDniPaciente;
    JButton jButtonSend;
    JButton jButtonExit;
    FormularioModificarPacienteFinal formularioModificarPacienteFinal;
    Paciente paciente;
    PacienteService pacienteService;

    public FormularioModificarPaciente(PanelManager panel) throws ServiceException, ParseException {
        this.panel=panel;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        formularioModificarPaciente = new JPanel();
        formularioModificarPaciente.setLayout(new GridLayout(4,2));
        jLabelDniPaciente = new JLabel("Dni del paciente a modificar");
        jTextFieldDniPaciente = new JTextField();
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarFormulario() {
        formularioModificarPaciente.add(jLabelDniPaciente);
        formularioModificarPaciente.add(jTextFieldDniPaciente);
        formularioModificarPaciente.add(jButtonExit);
        formularioModificarPaciente.add(jButtonSend);
        setFormatoJTextField(jTextFieldDniPaciente);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonExit.addActionListener(e -> {
            try {
                formularioAdmin = new FormularioAdmin(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioAdmin.getFormulario());
        });
        jButtonSend.addActionListener(e -> {
            pacienteService = new PacienteService();
            paciente = new Paciente();
            try {
                paciente.setDniPaciente(Integer.parseInt(jTextFieldDniPaciente.getText()));
                paciente = pacienteService.buscar(paciente);
                if (paciente==null){
                    JOptionPane.showMessageDialog(null,"Paciente no encontrado");
                }else {
                    formularioModificarPacienteFinal = new FormularioModificarPacienteFinal(panel,paciente);
                    panel.mostrar(formularioModificarPacienteFinal.getFormulario());
                }
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null,"Ingrese un valor valido");
            }
        });
    }

    public JPanel getFormulario() {
        return formularioModificarPaciente;
    }

    @Override
    public void decorar() {
        formularioModificarPaciente.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioModificarPaciente.setBackground(Color.lightGray);
        formularioModificarPaciente.setPreferredSize(new Dimension(220, 80));
        formularioModificarPaciente.setOpaque(true);
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
