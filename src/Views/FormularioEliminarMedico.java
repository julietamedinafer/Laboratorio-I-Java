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

public class FormularioEliminarMedico implements Formulario, DecorarFormulario, SetFormatoJTextField{
    JPanel formularioEliminarMedico;
    JButton jButtonSend;
    JButton jButtonExit;
    JLabel jLabelDniMedico;
    JTextField jTextFieldDniMedico;
    PanelManager panel;
    FormularioAdmin formularioAdmin;
    MedicoService medicoService;

    public FormularioEliminarMedico(PanelManager panel) throws ServiceException, ParseException {
        this.panel=panel;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        ///medicoService = new MedicoService();
        formularioEliminarMedico = new JPanel();
        formularioEliminarMedico.setLayout(new GridLayout(2,2));
        jLabelDniMedico = new JLabel("Dni");
        jTextFieldDniMedico = new JTextField();
        jButtonSend = new JButton("Eliminar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarFormulario() {
        formularioEliminarMedico.add(jLabelDniMedico);
        formularioEliminarMedico.add(jTextFieldDniMedico);
        formularioEliminarMedico.add(jButtonExit);
        formularioEliminarMedico.add(jButtonSend);
        setFormatoJTextField(jTextFieldDniMedico);
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
                Medico medico = new Medico();
                medicoService = new MedicoService();
                medico.setDniMedico(Integer.parseInt(jTextFieldDniMedico.getText()));
                if (medicoService.buscar(medico) != null) {
                    medicoService.eliminar(medico);
                    JOptionPane.showMessageDialog(null, "Medico eliminado");
                }else {
                    JOptionPane.showMessageDialog(null, "No se encontró el médico");
                }
                formularioAdmin = new FormularioAdmin(panel);
                panel.mostrar(formularioAdmin.getFormulario());
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null,"Error al eliminar medico" + ex.getMessage());
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null,"Dni inválido");
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public JPanel getFormulario() {
        return  formularioEliminarMedico;
    }

    @Override
    public void decorar() {
        formularioEliminarMedico.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioEliminarMedico.setBackground(Color.lightGray);
        formularioEliminarMedico.setPreferredSize(new Dimension(220, 80));
        formularioEliminarMedico.setOpaque(true);
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
