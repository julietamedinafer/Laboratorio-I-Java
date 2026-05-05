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

public class FormularioUsuarioPaciente extends JPanel implements Formulario, DecorarFormulario, SetFormatoJTextField{
    PanelManager panel;
    JPanel formularioUsuarioPaciente;
    FormularioSeleccionUsuario formularioSeleccionUsuario;
    FormularioTurnosPaciente formularioTurnosPaciente;
    JLabel jLabelDni;
    JTextField jTextFieldDni;
    JButton jButtonSend;
    JButton jButtonExit;
    TurnoService turnoService;
    public FormularioUsuarioPaciente(PanelManager panel) throws ServiceException, ParseException {
        this.panel=panel;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        turnoService = new TurnoService();
        formularioUsuarioPaciente = new JPanel();
        formularioUsuarioPaciente.setLayout(new GridLayout(2,2));
        jLabelDni = new JLabel("Ingrese su dni:");
        jButtonSend = new JButton("Enviar");
        jButtonExit = new JButton("Salir");
        jTextFieldDni = new JFormattedTextField();
        setFormatoJTextField(jTextFieldDni);
    }

    @Override
    public void agregarFormulario() {
        formularioUsuarioPaciente.add(jLabelDni);
        formularioUsuarioPaciente.add(jTextFieldDni);
        formularioUsuarioPaciente.add(jButtonExit);
        formularioUsuarioPaciente.add(jButtonSend);
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
                try {
                    ArrayList<Turno> turnos = turnoService.PacienteBuscaTurnos(Integer.parseInt(jTextFieldDni.getText()));
                    if (turnos.size() == 0) {
                        JOptionPane.showMessageDialog(null, "No hay turnos disponibles");
                    } else {
                        formularioTurnosPaciente = new FormularioTurnosPaciente(panel, turnos);
                        panel.mostrar(formularioTurnosPaciente.getFormulario());
                    }
                } catch (ServiceException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public JPanel getFormulario() {
        return formularioUsuarioPaciente;
    }

    public NumberFormatter getNumberFormatter(){
        NumberFormatter formatter = new NumberFormatter();
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(99999999); // Máximo 8 dígitos
        return formatter;
    }

    @Override
    public void decorar() {
        formularioUsuarioPaciente.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioUsuarioPaciente.setBackground(Color.lightGray);
        formularioUsuarioPaciente.setPreferredSize(new Dimension(220, 80));
        formularioUsuarioPaciente.setOpaque(true);

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
