package servidor;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import api.ServicioDatosInterface;
import api.ServicioGestorInterface;
import data_model.ErrorResult;
import data_model.Result;
import data_model.SuccessResult;
import data_model.Trino;
import data_model.User;
import data_model.requests.GetUser;
import data_model.requests.GetUsers;
import data_model.requests.Request;

public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {
	private static final long serialVersionUID = 3L;

	public ServicioGestorImpl() throws RemoteException {
        super();
    }
	
	@Override
	public String solicitarInfoUsuario(String username) throws RemoteException {
		System.out.println("Solicitud de informacion de usuario recibida.");
    	try {
	        Registry registry = LocateRegistry.getRegistry("localhost", 45002);
	        ServicioDatosInterface service =
	            (ServicioDatosInterface) registry.lookup("ServicioDatos");

	        Result<User> result = service.realizarQuery(new GetUser(username));
	        
	        switch(result)
	        {
		        case SuccessResult<User> ok -> {
		        	User user = (User) ok.data();
			        StringBuilder sb = new StringBuilder();
			        sb.append("--- User Information ---").append(System.lineSeparator());
			        sb.append("Username: "+ user.username).append(System.lineSeparator());
			        sb.append("Password: "+ user.password).append(System.lineSeparator());
			        sb.append("Baneado ").append(user.isBanned).append(System.lineSeparator());
			        sb.append("-----------------------");
			        return sb.toString();
		        }
		        case ErrorResult err -> {
		        	System.out.println("No se pudo recuperar la informacion de usuario");
		        	System.out.println(err.message());
		        }
	        }
	        
	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
    	return new String();
	}

	@Override
	public boolean enviarTrino(Trino trino) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String listarUsuarios() throws RemoteException {
		try {
	        Registry registry = LocateRegistry.getRegistry("localhost", 45002);
	        ServicioDatosInterface service =
	            (ServicioDatosInterface) registry.lookup("ServicioDatos");

	        Result<ArrayList<String>> result = service.realizarQuery(new GetUsers());

	        switch(result)
	        {
		        case SuccessResult<ArrayList<String>> ok -> {
		        	List<String> users = (List<String>) ok.data();
			        StringBuilder sb = new StringBuilder();
			        users.forEach(username -> sb.append(username).append(System.lineSeparator()));
			        return sb.toString();
		        }
		        case ErrorResult err -> {
		        	System.out.println("No se pudo recuperar la informacion de usuario");
		        	System.out.println(err.message());
		        }
	        }
	        
	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
    	return new String();
	}

	@Override
	public boolean editarSubscripcion(String usernameSource, String usernameTarget, boolean isSubscribed)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
	
}