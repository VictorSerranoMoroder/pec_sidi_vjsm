package cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import api.ServicioAutenticacionInterface;
import api.ServicioGestorInterface;
import data_model.AutenticacionRequest;
import data_model.RegistroRequest;
import data_model.Trino;

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
	            case 2 -> launch_send_trino();
	            case 3 -> launch_user_list();
	            case 4 -> launch_follow_user();
	            case 5 -> launch_unfollow_user();
	            case 6 -> System.exit(0);
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
	        	ServicioGestorInterface gestorService = 
	        			(ServicioGestorInterface) registry.lookup("GestorService");
	        	// Registrar callback para recibir trinos
	        	gestorService.registrarTrinoCallback(username, new CallbackUsuarioImpl());
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
	
	private static void launch_send_trino()
	{
		try {
	        Registry registry = LocateRegistry.getRegistry("localhost", 45001);
	        ServicioGestorInterface service =
	            (ServicioGestorInterface) registry.lookup("GestorService");

	        System.out.print("Escribe tu trino: ");
		    String trino = scanner.nextLine();
	        
	        boolean result = service.enviarTrino(new Trino(loggedUser, trino));

	        if (result)
	        {
	        	System.out.println("Trino enviado con exito.");
	        }
	        else 
	        {
	        	System.out.println("Error al enviar el trino.");
	        }
	        
	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
	}
	
	private static void launch_unfollow_user()
	{
		try {
	        Registry registry = LocateRegistry.getRegistry("localhost", 45001);
	        ServicioGestorInterface service =
	            (ServicioGestorInterface) registry.lookup("GestorService");
	        
	        System.out.print("¿Que usuario quieres dejar de seguir?: ");
		    String userToFollow = scanner.nextLine();

	        boolean result = service.editarSubscripcion(loggedUser, userToFollow, false);

	        if (result)
	        {
	        	System.out.println("Ha dejado de seguir al usuario: " + userToFollow);
	        }
	        else 
	        {
	        	System.out.println("No se ha podido dejar de seguir al usuario " + userToFollow);
	        }
	        
	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
	}
	
	private static void launch_follow_user()
	{
		try {
	        Registry registry = LocateRegistry.getRegistry("localhost", 45001);
	        ServicioGestorInterface service =
	            (ServicioGestorInterface) registry.lookup("GestorService");
	        
	        System.out.print("¿Que usuario quieres seguir?: ");
		    String userToFollow = scanner.nextLine();

	        boolean result = service.editarSubscripcion(loggedUser, userToFollow, true);

	        if (result)
	        {
	        	System.out.println("Ha comenzado a seguir al usuario: " + userToFollow);
	        }
	        else 
	        {
	        	System.out.println("No se ha podido seguir al usuario " + userToFollow);
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
}
