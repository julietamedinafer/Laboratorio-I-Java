package Views;

import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class FormularioAdmin extends JPanel implements Formulario, DecorarFormulario {
    FormularioSeleccionUsuario formularioSeleccionUsuario;
    FormularioAgregarMedico formularioAgregarMedico;
    FormularioAgregarPaciente formularioAgregarPaciente;
    FormularioAgregarTurno formularioAgregarTurno;
    FormularioReporte formularioReporte;
    JPanel formularioAdmin;
    PanelManager panel;
    JButton jButtonRegistrarMedico;
    JButton jButtonRegistrarPaciente;
    JButton jButtonRegistrarTurno;
    JButton jButonEliminarTurno;
    FormularioEliminarTurno formularioEliminarTurno;
    JButton jButtonEliminarPaciente;
    FormularioEliminarPaciente formularioEliminarPaciente;
    JButton jButtonEliminarMedico;
    FormularioEliminarMedico formularioEliminarMedico;
    JButton jButtonModificarTurno;
    FormularioModificarTurno formularioModificarTurno;
    JButton jButtonModificarPaciente;
    FormularioModificarPaciente formularioModificarPaciente;
    JButton jButtonModificarMedico;
    FormularioModificarMedico formularioModificarMedico;
    JButton jButtonReporte;
    JButton jButtonVolver;

    public FormularioAdmin(PanelManager panel) throws ServiceException, ParseException {
        this.panel = panel;
        creadorFormulario();
        agregarFormulario();
        agregarFuncionesBotones();
        decorar();
    }

    @Override
    public void decorar() {
        formularioAdmin.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        formularioAdmin.setBackground(Color.lightGray);
        formularioAdmin.setPreferredSize(new Dimension(450, 160));
        formularioAdmin.setOpaque(true);
    }

    @Override
    public void creadorFormulario() throws ServiceException, ParseException {
        formularioAdmin = new JPanel();
        formularioAdmin.setLayout(new GridLayout(4, 3));
        jButtonRegistrarMedico = new JButton("Registrar Medico");
        jButtonRegistrarPaciente = new JButton("Registrar Paciente");
        jButtonRegistrarTurno = new JButton("Registrar Turno");
        jButtonEliminarMedico = new JButton("Eliminar Medico");
        jButtonEliminarPaciente = new JButton("Eliminar Paciente");
        jButonEliminarTurno = new JButton("Eliminar Turno");
        jButtonModificarMedico = new JButton("Modificar Medico");
        jButtonModificarPaciente = new JButton("Modificar Paciente");
        jButtonModificarTurno = new JButton("Modificar Turno");
        jButtonReporte = new JButton("Reporte");
        jButtonVolver = new JButton("Atrás");
    }

    @Override
    public void agregarFormulario() {
        formularioAdmin.add(jButtonRegistrarMedico);
        formularioAdmin.add(jButtonRegistrarPaciente);
        formularioAdmin.add(jButtonRegistrarTurno);
        formularioAdmin.add(jButtonEliminarMedico);
        formularioAdmin.add(jButtonEliminarPaciente);
        formularioAdmin.add(jButonEliminarTurno);
        formularioAdmin.add(jButtonModificarMedico);
        formularioAdmin.add(jButtonModificarPaciente);
        formularioAdmin.add(jButtonModificarTurno);
        formularioAdmin.add(jButtonReporte);
        formularioAdmin.add(new JLabel());
        formularioAdmin.add(jButtonVolver);
    }

    @Override
    public void agregarFuncionesBotones() {
        jButtonVolver.addActionListener(e -> {
            try {
                formularioSeleccionUsuario = new FormularioSeleccionUsuario(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioSeleccionUsuario.getFormulario());
        });{

        }

        jButtonRegistrarMedico.addActionListener(e -> {
            try {
                formularioAgregarMedico = new FormularioAgregarMedico(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioAgregarMedico.getFormulario());
        });

        jButtonRegistrarPaciente.addActionListener(e -> {
            try {
                formularioAgregarPaciente = new FormularioAgregarPaciente(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioAgregarPaciente.getFormulario());
        });

        jButtonRegistrarTurno.addActionListener(e -> {
            try {
                formularioAgregarTurno = new FormularioAgregarTurno(panel);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioAgregarTurno.getFormulario());
        });

        jButtonEliminarMedico.addActionListener(e -> {
            try {
                formularioEliminarMedico = new FormularioEliminarMedico(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioEliminarMedico.getFormulario());
        });

        jButtonEliminarPaciente.addActionListener(e -> {
            try {
                formularioEliminarPaciente = new FormularioEliminarPaciente(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioEliminarPaciente.getFormulario());
        });
        jButonEliminarTurno.addActionListener(e -> {
            try {
                formularioEliminarTurno = new FormularioEliminarTurno(panel);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioEliminarTurno.getFormulario());
        });

        jButtonModificarMedico.addActionListener(e -> {
            try {
                formularioModificarMedico = new FormularioModificarMedico(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioModificarMedico.getFormulario());
        });

        jButtonModificarPaciente.addActionListener(e -> {
            try {
                formularioModificarPaciente = new FormularioModificarPaciente(panel);
            } catch (ServiceException | ParseException ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioModificarPaciente.getFormulario());
        });
        jButtonModificarTurno.addActionListener(e -> {
            try {
                formularioModificarTurno = new FormularioModificarTurno(panel);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioModificarTurno.getFormulario());
        });
        jButtonReporte.addActionListener(e -> {
            try {
                formularioReporte = new FormularioReporte(panel);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            panel.mostrar(formularioReporte.getFormulario());
        });
    }

    @Override
    public JPanel getFormulario() {
        return formularioAdmin;
    }
}
