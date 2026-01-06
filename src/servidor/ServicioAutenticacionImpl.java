package servidor;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import api.ServicioAutenticacionInterface;
import api.ServicioDatosInterface;
import data_model.AutenticacionRequest;
import data_model.QueryRequest;
import data_model.QueryResult;
import data_model.QueryRequest.ProcedureType;
import data_model.RegistroRequest;

public class ServicioAutenticacionImpl extends UnicastRemoteObject implements ServicioAutenticacionInterface {
	private static final long serialVersionUID = 3L;

	public ServicioAutenticacionImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean AutenticarUsuario(AutenticacionRequest request) throws RemoteException {
    	System.out.println(request.username);
    	System.out.println(request.password);
        return false;
    }

	@Override
	public boolean RegistrarUsuario(RegistroRequest request) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(request.usuario.username);
    	System.out.println(request.usuario.password);
    	
    	try {
	        Registry registry = LocateRegistry.getRegistry("localhost", 45002);
	        ServicioDatosInterface service =
	            (ServicioDatosInterface) registry.lookup("ServicioDatos");

	        QueryResult<Serializable> result = service.ejecutarProcedimiento(new QueryRequest(ProcedureType.ADD_USER, request.usuario));

	        System.out.println(
	        		result.success ? "Usuario registrado" : "Registro fallido"
	        );
	        
	        System.out.println(result.data.toString());
	        
	        return result.success;

	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
    	
		return false;
	}
}
