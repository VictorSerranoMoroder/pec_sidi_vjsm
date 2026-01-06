package cliente;

import java.nio.charset.StandardCharsets;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import api.ServicioAutenticacionInterface;
import data_model.AutenticacionRequest;
import data_model.RegistroRequest;

public class Cliente {
	
	private static Scanner scanner;
	private static MessageDigest hashProvider;
	
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
	            case 2 -> System.out.println("Login not implemented");
	            case 3 -> System.exit(0);
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

	    String hash = bytesToHex(
	        hashProvider.digest(password.getBytes(StandardCharsets.UTF_8))
	    );
		
	    try {
	        Registry registry = LocateRegistry.getRegistry("localhost", 45001);
	        ServicioAutenticacionInterface service =
	            (ServicioAutenticacionInterface) registry.lookup("RegisterService");

	        boolean success = service.RegistrarUsuario(new RegistroRequest(username,password));

	        System.out.println(
	            success ? "Usuario registrado" : "Registro fallido"
	        );

	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
	}
	
	// Code snippet from https://www.baeldung.com/sha-256-hashing-java
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	public static void main(String[] str) {
		scanner = new Scanner(System.in);
		try {
			hashProvider = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
