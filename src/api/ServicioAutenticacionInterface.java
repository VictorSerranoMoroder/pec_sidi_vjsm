package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import data_model.AutenticacionRequest;
import data_model.RegistroRequest;

public interface ServicioAutenticacionInterface extends Remote {
    public boolean AutenticarUsuario(AutenticacionRequest request) throws RemoteException;
    public boolean RegistrarUsuario(RegistroRequest request) throws RemoteException;
}
