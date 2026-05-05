package Views;

import Service.ServiceException;

import javax.swing.*;
import java.text.ParseException;

public interface Formulario {
    void creadorFormulario() throws ServiceException, ParseException;
    public void agregarFormulario();
    public void agregarFuncionesBotones();
    public JPanel getFormulario();
}
