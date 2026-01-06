package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import data_model.AutenticacionRequest;
import data_model.RegistroRequest;

public interface ServicioGestorInterface extends Remote {
	public boolean RegistrarUsuario(RegistroRequest request) throws RemoteException;
}
