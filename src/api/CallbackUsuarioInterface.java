package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import data_model.Trino;

public interface CallbackUsuarioInterface extends Remote {
	void recibirTrino(Trino trino) throws RemoteException;
}
