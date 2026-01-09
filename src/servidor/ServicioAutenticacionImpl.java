package servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import api.ServicioAutenticacionInterface;
import api.ServicioDatosInterface;
import data_model.AutenticacionRequest;
import data_model.ErrorResult;
import data_model.Result;
import data_model.SuccessResult;
import data_model.RegistroRequest;
import data_model.User;
import data_model.requests.AddUser;
import data_model.requests.GetUser;

public class ServicioAutenticacionImpl extends UnicastRemoteObject implements ServicioAutenticacionInterface {
	private static final long serialVersionUID = 3L;

	public ServicioAutenticacionImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean AutenticarUsuario(AutenticacionRequest request) throws RemoteException {
    	System.out.println("Solicitud de Autenticacion recibida.");
    	
    	try {
	        Registry registry = LocateRegistry.getRegistry("localhost", 45002);
	        ServicioDatosInterface service =
	            (ServicioDatosInterface) registry.lookup("ServicioDatos");

	        Result<User> result = service.realizarQuery(new GetUser(request.usuario.username));
	        
	        switch(result)
	        {
		        case SuccessResult<User> ok -> {
		        	User data = ok.data();
		        	return request.usuario.username.equals(data.username) && request.usuario.password.equals(data.password);
		        }
		        case ErrorResult err -> {
		        	System.out.println("Autentificacion fallida");
		        	System.out.println(err.message());
		        }
	        }
	        
	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
        return false;
    }

	@Override
	public boolean RegistrarUsuario(RegistroRequest request) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Solicitud de Registro recibida.");
    	
    	try {
	        Registry registry = LocateRegistry.getRegistry("localhost", 45002);
	        ServicioDatosInterface service =
	            (ServicioDatosInterface) registry.lookup("ServicioDatos");

	        Result result = service.ejecutarProcedimiento(new AddUser(request.usuario));

	        switch(result)
	        {
		        case SuccessResult<?> ok -> {
		        	return true;
		        }
		        case ErrorResult err -> {
		        	return false;
		        }
	        }
	        
	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
    	
		return false;
	}
}
