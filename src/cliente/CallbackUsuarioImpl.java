package cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import api.CallbackUsuarioInterface;
import data_model.Trino;

public class CallbackUsuarioImpl extends UnicastRemoteObject
		implements CallbackUsuarioInterface {

	public CallbackUsuarioImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	    }

	@Override
	public void recibirTrino(Trino trino) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("---------");
		System.out.println("Nuevo trino de: " + trino.GetNickPropietario());
		System.out.println(trino.GetTrino());
		System.out.println("---------");
	}

}
