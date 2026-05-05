package Views;

import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

public class PanelManager{
    public FormularioSeleccionUsuario formularioSeleccionUsuario;
    //public FormularioAdmin formularioAdmin;

    JFrame ventana;
    public PanelManager() throws ServiceException, ParseException {
        ventana=new JFrame("Consultorio");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        formularioSeleccionUsuario = new FormularioSeleccionUsuario(this);
        mostrar(formularioSeleccionUsuario.getFormulario());
        ventana.setVisible(true);
    }
    public void mostrar(JPanel panel){
        ventana.getContentPane().removeAll();
        ventana.getContentPane().add(BorderLayout.SOUTH, panel);
        ventana.getContentPane().validate();
        ventana.getContentPane().repaint();
        ventana.pack();
        ventana.setLocationRelativeTo(null);

    }

    public JFrame getVentana(){
        return ventana;
    }

    public void cerrarVentana(){
        ventana.dispose();
    }
}
