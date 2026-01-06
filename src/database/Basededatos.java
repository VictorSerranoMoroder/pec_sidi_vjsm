package database;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import api.ServicioAutenticacionInterface;
import api.ServicioDatosInterface;
import servidor.ServicioAutenticacionImpl;

public class Basededatos {
	public static void main(String[] str)
	{
		// Start RMI registry on port 1099
        Registry registry;
		try {
			registry = LocateRegistry.createRegistry(45002);
		
	        // Create service
	        ServicioDatosInterface service = new ServicioDatosImpl();
	
	        // Bind service to registry
	        registry.rebind("ServicioDatos", service);
        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("RMI Database is running...");
	}
}
