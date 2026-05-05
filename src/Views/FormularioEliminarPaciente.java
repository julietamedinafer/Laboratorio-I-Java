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

public class FormularioEliminarPaciente implements Formulario, DecorarFormulario, SetFormatoJTextField{
    PacienteService medicoService;
    JPanel formularioEliminarPaciente;
    JButton jButtonSend;
    JButton jButtonExit;
    JLabel jLabelDniPaciente;
    JTextField jTextFieldDniPaciente;
    PanelManager panel;
    FormularioAdmin formularioAdmin;
    public FormularioEliminarPaciente(PanelManager panel) throws ServiceException, ParseException {
        this.panel=panel;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        medicoService = new PacienteService();
        formularioEliminarPaciente = new JPanel();
        formularioEliminarPaciente.setLayout(new GridLayout(2,2));
        jLabelDniPaciente = new JLabel("Dni");
        jTextFieldDniPaciente = new JTextField();
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarFormulario() {
        formularioEliminarPaciente.add(jLabelDniPaciente);
        formularioEliminarPaciente.add(jTextFieldDniPaciente);
        formularioEliminarPaciente.add(jButtonExit);
        formularioEliminarPaciente.add(jButtonSend);
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
            try {
                Paciente paciente = new Paciente();
                paciente.setDniPaciente(Integer.parseInt(jTextFieldDniPaciente.getText()));
                if (medicoService.buscar(paciente) != null) {
                    medicoService.eliminar(paciente);
                    JOptionPane.showMessageDialog(null, "Paciente eliminado");
                }else {
                    JOptionPane.showMessageDialog(null, "No se encontró el paciente");
                }
                formularioAdmin = new FormularioAdmin(panel);
                panel.mostrar(formularioAdmin.getFormulario());
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null,"Error al eliminar paciente");
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null,"Ingrese un numero");
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public JPanel getFormulario() {
        return formularioEliminarPaciente;
    }

    @Override
    public void decorar() {
        formularioEliminarPaciente.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioEliminarPaciente.setBackground(Color.lightGray);
        formularioEliminarPaciente.setPreferredSize(new Dimension(220, 80));
        formularioEliminarPaciente.setOpaque(true);
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
