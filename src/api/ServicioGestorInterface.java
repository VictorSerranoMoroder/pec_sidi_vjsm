package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import data_model.RegistroRequest;
import data_model.Trino;

public interface ServicioGestorInterface extends Remote {
	public String solicitarInfoUsuario(String username) throws RemoteException;
	public boolean enviarTrino(Trino trino) throws RemoteException;
	public String listarUsuarios() throws RemoteException;
	public boolean editarSubscripcion(String usernameSource, String usernameTarget, boolean isSubscribed) throws RemoteException;
}
