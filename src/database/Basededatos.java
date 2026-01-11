package database;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

import api.ServicioAutenticacionInterface;
import api.ServicioDatosInterface;
import data_model.ErrorResult;
import data_model.Result;
import data_model.SuccessResult;
import data_model.Trino;
import data_model.requests.GetAllTrinos;
import data_model.requests.GetTrinos;
import data_model.requests.GetUsers;
import servidor.ServicioAutenticacionImpl;

public class Basededatos {
	public static final String SERVICENAME = "ServicioDatos";
	public static final int PORT = 45002; 
	private static Scanner scanner;
	private static ServicioDatosInterface service;
	
	private static void launchMainMenu() {
	    while (true) {
	        System.out.println("""
	            1.- Información de la Base de Datos.
	            2.- Listar Trinos.
	            3.- Salir.
	            """);

	        int option = Integer.parseInt(scanner.nextLine());

	        switch (option) {
	            case 1 -> launchDatabaseInfoQuery();
	            case 2 -> launchListTrinos();
	            case 3 -> System.exit(0);
	            default -> System.out.println("Opción inválida");
	        }
	    }
	}
	
	private static void launchDatabaseInfoQuery()
	{
		System.out.println("--- Informacion de la Base de Datos ---");
		System.out.println("Servicios remotos registrados:");
		System.out.println("rmi://localhost:"+ PORT +"/ServicioDatos/Basededatos\"");
		System.out.println("---------------------------------------");
	}
	
	private static void launchListTrinos()
	{
		try {
			StringBuilder sb = new StringBuilder();
			Result<ArrayList<Trino>> result = service.realizarQuery(new GetAllTrinos());
			
			switch(result)
			{
				case SuccessResult<ArrayList<Trino>> ok: {
					System.out.println("Éxito recuperando lista de trinos, mostrando trinos por pantalla:");
					sb.append("------------------");
					ok.data().forEach(trino -> {
						sb.append("--- Autor: " + trino.GetNickPropietario()).append(System.lineSeparator());
						sb.append(trino.GetTrino()).append(System.lineSeparator());
						sb.append("------------------").append(System.lineSeparator());
					});
					System.out.println(sb.toString());
				}
					break;
				case ErrorResult err: {
					System.out.println("Error recuperando la lista de trinos.");
				}
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] str)
	{
		scanner = new Scanner(System.in);
		// Start RMI registry on port 1099
        Registry registry;
		try {
			registry = LocateRegistry.createRegistry(45002);
		
	        // Create service
	        service = new ServicioDatosImpl();
	
	        // Bind service to registry
	        registry.rebind(SERVICENAME, service);
        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("RMI Database is running...");
        launchMainMenu();
	}
}
