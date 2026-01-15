/*
 * Servidor.java
 * Copyright (C) 2025 Víctor Serrano Moroder vserrano157@alumno.uned.es
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

import api.ServicioAutenticacionInterface;
import api.ServicioDatosInterface;
import api.ServicioGestorInterface;
import data_model.User;
import data_model.db_requests.BanUser;
import data_model.db_requests.GetUser;
import data_model.db_requests.GetUsers;
import data_model.db_requests.UnbanUser;
import data_model.results.ErrorResult;
import data_model.results.Result;
import data_model.results.SuccessResult;
import database.Basededatos;

public class Servidor {
	public static final String AUTHSERVICENAME = "AuthService";
	public static final String GESTSERVICENAME = "GestorService";
	public static final int PORT = 45001;
	private static Scanner scanner;
	private static ServicioAutenticacionInterface authService;
	private static ServicioGestorInterface gestorService;

	private static void launchMainMenu() {
	    while (true) {
	        System.out.println("""
	            1.- Información del Servidor.
	            2.- Listar Usuarios Registrados.
	            3.- Listar Usuarios Logueados.
	            4.- Bloquear (banear) usuario.
	            5.- Desbloquear usuario.
	            6.- Salir.
	            """);

	        int option = Integer.parseInt(scanner.nextLine());

	        switch (option) {
	            case 1 -> launchServerInfoQuery();
	            case 2 -> launchListRegisteredUsers();
	            case 3 -> launchListLoggedInUsers();
	            case 4 -> launchBanUser();
	            case 5 -> launchUnbanUser();
	            case 6 -> System.exit(0);
	            default -> System.out.println("Opción inválida");
	        }
	    }
	}

	private static void launchServerInfoQuery()
	{
		System.out.println("--- Informacion de la Base de Datos ---");
		System.out.println("Servicios remotos registrados:");
		System.out.println("rmi://localhost:"+ PORT +"/"+AUTHSERVICENAME+"-/Servidor\"");
		System.out.println("rmi://localhost:"+ PORT +"/"+gestorService+"-/Servidor\"");
		System.out.println("---------------------------------------");
	}

	private static void launchListRegisteredUsers()
	{
		try {
	        Registry registry = LocateRegistry.getRegistry("localhost", Basededatos.PORT);
	        ServicioDatosInterface service =
	            (ServicioDatosInterface) registry.lookup(Basededatos.SERVICENAME);

	        Result<ArrayList<String>> result = service.realizarQuery(new GetUsers());

	        switch(result)
	        {
		        case SuccessResult<ArrayList<String>> ok -> {
		        	StringBuilder sb = new StringBuilder();
			        ok.data().forEach(username -> {
			        	sb.append(username).append(System.lineSeparator());
			        });
			        System.out.println(sb.toString());
		        }
		        case ErrorResult err -> {
		        	System.out.println(err.message());
		        }
	        }

	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
	}

	private static void launchListLoggedInUsers()
	{
		try {
	        Registry registry = LocateRegistry.getRegistry("localhost", Basededatos.PORT);
	        ServicioDatosInterface service =
	            (ServicioDatosInterface) registry.lookup(Basededatos.SERVICENAME);

	        // Recoger los usuarios del sistema
	        Result<ArrayList<String>> result = service.realizarQuery(new GetUsers());

	        switch(result)
	        {
		        case SuccessResult<ArrayList<String>> ok -> {
		        	StringBuilder sb = new StringBuilder();
		        	sb.append("----- "+"Usuarios Conectados"+" -----").append(System.lineSeparator());
		        	ok.data().forEach(username -> {
		        		// Por cada usuario registrado hay que recuperar sus datos
		        		try {
							Result<User> userResult = service.realizarQuery(new GetUser(username));

							switch (userResult)
							{
								case SuccessResult<User> userOk -> {
									// Si el usuario está online se añade para mostrar por pantalla
									if (userOk.data().isOnline)
									{
										sb.append(userOk.data().username).append(System.lineSeparator());
									}
								}
								case ErrorResult userErr -> {
									System.out.println(userErr.message());
								}
							}
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	});
		        	sb.append("----------");
		        	System.out.println(sb.toString());
		        }
		        case ErrorResult err -> {
		        	System.out.println(err.message());
		        }
	        }
	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
	}

	private static void launchBanUser()
	{
		System.out.print("¿Que usuario quieres bloquear?: ");
	    String userToBan = scanner.nextLine();

	    try {
	        Registry registry = LocateRegistry.getRegistry("localhost", Basededatos.PORT);
	        ServicioDatosInterface service =
	            (ServicioDatosInterface) registry.lookup(Basededatos.SERVICENAME);

	        Result result = service.ejecutarProcedimiento(new BanUser(userToBan));

	        switch(result)
	        {
		        case SuccessResult<?> ok -> {
		        	System.out.println("Usuario bloqueado: "+userToBan);
		        }
		        case ErrorResult err -> {
		        	System.out.println(err.message());
		        }
	        }
	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
	}

	private static void launchUnbanUser()
	{
		System.out.print("¿Que usuario quieres desbloquear?: ");
	    String userToUnban = scanner.nextLine();

	    try {
	        Registry registry = LocateRegistry.getRegistry("localhost", Basededatos.PORT);
	        ServicioDatosInterface service =
	            (ServicioDatosInterface) registry.lookup(Basededatos.SERVICENAME);

	        Result result = service.ejecutarProcedimiento(new UnbanUser(userToUnban));

	        switch(result)
	        {
		        case SuccessResult<?> ok -> {
		        	System.out.println("Usuario desbloqueado: "+userToUnban);
		        }
		        case ErrorResult err -> {
		        	System.out.println(err.message());
		        }
	        }
	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
	}

	public static void main(String[] str) throws Exception
	{
		// Start RMI registry
        Registry registry = LocateRegistry.createRegistry(PORT);
        scanner = new Scanner(System.in);

        // Create services
        authService = new ServicioAutenticacionImpl();
        gestorService = new ServicioGestorImpl();

        // Bind service to registry
        registry.rebind(AUTHSERVICENAME, authService);
        registry.rebind(GESTSERVICENAME, gestorService);

        System.out.println("RMI Server is running...");
        launchMainMenu();
	}
}
