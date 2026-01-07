package servidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import api.ServicioAutenticacionInterface;

public class Servidor {
	public static void main(String[] str) throws Exception
	{
		// Start RMI registry on port 1099
        Registry registry = LocateRegistry.createRegistry(45001);

        // Create service
        ServicioAutenticacionInterface service = new ServicioAutenticacionImpl();

        // Bind service to registry
        registry.rebind("AuthService", service);

        System.out.println("RMI Server is running...");
	}
}
