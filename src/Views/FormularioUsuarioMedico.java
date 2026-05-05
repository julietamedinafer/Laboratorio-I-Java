package Views;

import Entidades.Turno;
import Service.ServiceException;
import Service.TurnoService;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

public class FormularioUsuarioMedico extends JPanel implements Formulario, DecorarFormulario, SetFormatoJTextField{
    PanelManager panel;
    JPanel formularioUsuarioMedico;
    FormularioSeleccionUsuario formularioSeleccionUsuario;
    FormularioTurnosMedicos formularioTurnosMedicos;
    TurnoService turnoService;
    JLabel jLabelDni;
    JTextField jTextFieldDni;
    JLabel jLabelFecha;
    JFormattedTextField jTextFieldFecha;
    JButton jButtonSend;
    JButton jButtonExit;
    public FormularioUsuarioMedico(PanelManager panel) throws ServiceException, ParseException {
        this.panel=panel;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        turnoService = new TurnoService();
        formularioUsuarioMedico = new JPanel();
        formularioUsuarioMedico.setLayout(new GridLayout(3,2));
        jLabelDni = new JLabel("ingrese su dni");
        jTextFieldDni = new JTextField();
        jLabelFecha = new JLabel("Fecha");
        jTextFieldFecha = new JFormattedTextField(createMaskFormatter());
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
    }

    private MaskFormatter createMaskFormatter() {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("####/##/##");
            formatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }

    @Override
    public void agregarFormulario() {
        formularioUsuarioMedico.add(jLabelDni);
        formularioUsuarioMedico.add(jTextFieldDni);
        formularioUsuarioMedico.add(jLabelFecha);
        formularioUsuarioMedico.add(jTextFieldFecha);
        formularioUsuarioMedico.add(jButtonExit);
        formularioUsuarioMedico.add(jButtonSend);
        setFormatoJTextField(jTextFieldDni);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formularioSeleccionUsuario = new FormularioSeleccionUsuario(panel);
                } catch (ServiceException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
                panel.mostrar(formularioSeleccionUsuario.getFormulario());
            }
        });
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fechaText = jTextFieldFecha.getText().trim();
                String dniText = jTextFieldDni.getText().trim();
                try {
                    ArrayList turnos = turnoService.MedicoBuscaTurnos(fechaText, Integer.parseInt(dniText));
                    if (turnos.size() == 0) {
                        JOptionPane.showMessageDialog(null, "No hay turnos disponibles");
                    } else {
                        formularioTurnosMedicos = new FormularioTurnosMedicos(panel, turnos);
                        panel.mostrar(formularioTurnosMedicos.getFormulario());
                    }
                } catch (ServiceException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public JPanel getFormulario() {
        return formularioUsuarioMedico;
    }

    @Override
    public void decorar() {
        formularioUsuarioMedico.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioUsuarioMedico.setBackground(Color.lightGray);
        formularioUsuarioMedico.setPreferredSize(new Dimension(220, 120));
        formularioUsuarioMedico.setOpaque(true);
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
