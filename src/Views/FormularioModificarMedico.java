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

public class FormularioModificarMedico implements Formulario, DecorarFormulario, SetFormatoJTextField{
    JPanel formularioModificarMedico;
    PanelManager panel;
    FormularioAdmin formularioAdmin;
    JLabel jLabelDniMedico;
    JTextField jTextFieldDniMedico;
    JButton jButtonSend;
    JButton jButtonExit;
    FormularioModificarMedicoFinal formularioModificarMedicoFinal;
    Medico medico;
    MedicoService medicoService;

    public FormularioModificarMedico(PanelManager panel) throws ServiceException, ParseException {
        this.panel=panel;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        formularioModificarMedico = new JPanel();
        formularioModificarMedico.setLayout(new GridLayout(4,2));
        jLabelDniMedico = new JLabel("Dni del medico a modificar");
        jTextFieldDniMedico = new JTextField();
        jButtonSend = new JButton("Buscar");
        jButtonExit = new JButton("Salir");
    }

    @Override
    public void agregarFormulario() {
        formularioModificarMedico.add(jLabelDniMedico);
        formularioModificarMedico.add(jTextFieldDniMedico);
        formularioModificarMedico.add(jButtonExit);
        formularioModificarMedico.add(jButtonSend);
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
            medicoService = new MedicoService();
            medico = new Medico();
            try {
                medico.setDniMedico(Integer.parseInt(jTextFieldDniMedico.getText()));
                medico = medicoService.buscar(medico);
                if (medico==null){
                    JOptionPane.showMessageDialog(null,"Medico no encontrado");
                }else {
                    formularioModificarMedicoFinal = new FormularioModificarMedicoFinal(panel,medico);
                    panel.mostrar(formularioModificarMedicoFinal.getFormulario());
                }
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public JPanel getFormulario() {
        return formularioModificarMedico;
    }

    @Override
    public void decorar() {
        formularioModificarMedico.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioModificarMedico.setBackground(Color.lightGray);
        formularioModificarMedico.setPreferredSize(new Dimension(220, 80));
        formularioModificarMedico.setOpaque(true);
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
