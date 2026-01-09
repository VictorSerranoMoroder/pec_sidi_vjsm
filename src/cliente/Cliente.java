package cliente;

import java.nio.charset.StandardCharsets;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import api.ServicioAutenticacionInterface;
import api.ServicioGestorInterface;
import data_model.AutenticacionRequest;
import data_model.RegistroRequest;
import data_model.requests.Request;

public class Cliente {
	
	private static Scanner scanner;
	private static String loggedUser;
	
	private static void launch_main_menu() {
	    while (true) {
	        System.out.println("""
	            1.- Registrar un nuevo usuario
	            2.- Hacer login
	            3.- Salir
	            """);

	        int option = Integer.parseInt(scanner.nextLine());

	        switch (option) {
	            case 1 -> launch_user_registration();
	            case 2 -> launch_user_login();
	            case 3 -> System.exit(0);
	            default -> System.out.println("Opción inválida");
	        }
	    }
	}
	
	private static void launch_user_menu()
	{
		while (true) {
	        System.out.println("""
	            1.- Información del Usuario.
	            2.- Enviar Trino
	            3.- Listar Usuarios
	            4.- Seguir a
	            5.- Dejar de seguir a
	            6.- Salir
	            """);

	        int option = Integer.parseInt(scanner.nextLine());

	        switch (option) {
	            case 1 -> launch_user_info();
	            case 2 -> launch_user_list();
	            case 3 -> System.out.println("Opcion no implementada");
	            case 4 -> System.out.println("Opcion no implementada");
	            case 5 -> System.out.println("Opcion no implementada");
	            case 6 -> System.out.println("Opcion no implementada");
	            default -> System.out.println("Opción inválida");
	        }
	    }
	}
	
	private static void launch_user_registration()
	{
		System.out.print("Introduce nombre de usuario: ");
	    String username = scanner.nextLine();

	    System.out.print("Introduce contraseña: ");
	    String password = scanner.nextLine();
		
	    try {
	        Registry registry = LocateRegistry.getRegistry("localhost", 45001);
	        ServicioAutenticacionInterface service =
	            (ServicioAutenticacionInterface) registry.lookup("AuthService");

	        boolean success = service.RegistrarUsuario(new RegistroRequest(username,password));

	        System.out.println(
	            success ? "Usuario registrado" : "Registro fallido"
	        );

	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
	}
	
	private static void launch_user_login()
	{
		System.out.print("Introduce nombre de usuario: ");
	    String username = scanner.nextLine();

	    System.out.print("Introduce contraseña: ");
	    String password = scanner.nextLine();
	    
	    try {
	        Registry registry = LocateRegistry.getRegistry("localhost", 45001);
	        ServicioAutenticacionInterface service =
	            (ServicioAutenticacionInterface) registry.lookup("AuthService");

	        boolean success = service.AutenticarUsuario(new AutenticacionRequest(username,password));

	        System.out.println(
	            success ? "Usuario Autenticado, bienvenido" : "Credenciales no correctas."
	        );
	        
	        if (success)
	        {
	        	loggedUser = username;
	        	launch_user_menu();
	        }

	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
	}
	
	private static void launch_user_info()
	{
		try {
	        Registry registry = LocateRegistry.getRegistry("localhost", 45001);
	        ServicioGestorInterface service =
	            (ServicioGestorInterface) registry.lookup("GestorService");

	        String result = service.solicitarInfoUsuario(loggedUser);

	        if (result.isEmpty())
	        {
	        	System.out.println("Error, no se pudo recoger la informacion del usuario.");
	        }
	        else 
	        {
	        	System.out.println(result);
	        }
	        
	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
	}
	
	private static void launch_user_list()
	{
		try {
	        Registry registry = LocateRegistry.getRegistry("localhost", 45001);
	        ServicioGestorInterface service =
	            (ServicioGestorInterface) registry.lookup("GestorService");

	        String result = service.listarUsuarios();

	        if (result.isEmpty())
	        {
	        	System.out.println("Error, no se pudo recoger la informacion de usuarios.");
	        }
	        else 
	        {
	        	System.out.println(result);
	        }
	        
	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
	}
	
	public static void main(String[] str) {
		scanner = new Scanner(System.in);
		launch_main_menu();
	}
	
	/*
	public static void main(String[] str) throws Exception {
		
		scanner = new Scanner(System.in);
		hashProvider = MessageDigest.getInstance("SHA-256");
		
        // Connect to registry
        Registry registry = LocateRegistry.getRegistry("localhost", 6969);

        // Lookup service
        RegisterService service =
                (RegisterService) registry.lookup("RegisterService");

        // Call remote method
        String response = service.registerUser("Víctor");

        System.out.println(response);
        
        scanner.close();
	}
	*/
}
