package DAO;

import java.util.ArrayList;

public interface DAO<T>{
    public void guardar(T elemento) throws DAOException;
    public void modificar(T elemento) throws DAOException;
    public void eliminar(T elemento) throws DAOException;
    public T buscar(int dni) throws DAOException;
    public ArrayList buscarTodos() throws DAOException;

}

//<T> indica que se trata de una interfaz generica. Luego cada clase que
//implemente esta interfaz va a especificar con que tipo de elemento
//va a trabajar