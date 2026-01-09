package servidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import api.ServicioAutenticacionInterface;
import api.ServicioGestorInterface;

public class Servidor {
	public static void main(String[] str) throws Exception
	{
		// Start RMI registry
        Registry registry = LocateRegistry.createRegistry(45001);

        // Create services
        ServicioAutenticacionInterface authService = new ServicioAutenticacionImpl();
        ServicioGestorInterface gestorService = new ServicioGestorImpl();

        // Bind service to registry
        registry.rebind("AuthService", authService);
        registry.rebind("GestorService", gestorService);

        System.out.println("RMI Server is running...");
	}
}
