/*
 * ServicioGestorImpl.java
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
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import api.CallbackUsuarioInterface;
import api.ServicioDatosInterface;
import api.ServicioGestorInterface;
import data_model.Trino;
import data_model.User;
import data_model.db_requests.AddFollower;
import data_model.db_requests.AddTrino;
import data_model.db_requests.GetFollowers;
import data_model.db_requests.GetUser;
import data_model.db_requests.GetUsers;
import data_model.db_requests.RemoveFollower;
import data_model.results.ErrorResult;
import data_model.results.Result;
import data_model.results.SuccessResult;
import database.Basededatos;

public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {
	private static final long serialVersionUID = 3L;

	private HashMap<String, CallbackUsuarioInterface> callbackMap = new HashMap<>();

	public ServicioGestorImpl() throws RemoteException {
        super();
    }

	@Override
	public String solicitarInfoUsuario(String username) throws RemoteException {
		System.out.println("Solicitud de informacion de usuario recibida.");
    	try {
	        Registry registry = LocateRegistry.getRegistry("localhost", Basededatos.PORT);
	        ServicioDatosInterface service =
	            (ServicioDatosInterface) registry.lookup(Basededatos.SERVICENAME);

	        Result<User> result = service.realizarQuery(new GetUser(username));

	        switch(result)
	        {
		        case SuccessResult<User> ok -> {
		        	User user = (User) ok.data();
			        StringBuilder sb = new StringBuilder();
			        sb.append("--- User Information ---").append(System.lineSeparator());
			        sb.append("Username: "+ user.username).append(System.lineSeparator());
			        sb.append("Password: "+ user.password).append(System.lineSeparator());
			        sb.append("Baneado ").append(user.isBanned).append(System.lineSeparator());
			        sb.append("-----------------------");
			        return sb.toString();
		        }
		        case ErrorResult err -> {
		        	System.out.println("No se pudo recuperar la informacion de usuario");
		        	System.out.println(err.message());
		        }
	        }

	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
    	return new String();
	}

	@Override
	public boolean enviarTrino(Trino trino) throws RemoteException {
		try {
	        Registry registry = LocateRegistry.getRegistry("localhost", Basededatos.PORT);
	        ServicioDatosInterface service =
	            (ServicioDatosInterface) registry.lookup(Basededatos.SERVICENAME);

	        Result result = service.ejecutarProcedimiento(new AddTrino(trino));

	        switch(result)
	        {
		        case SuccessResult<?> ok -> {
		        	// El trino ha sido añadido con éxito a la base de datos
		        	// ahora hay que realizar un broadcast de este trino a todos sus seguidores
		        	Result<ArrayList<String>> followerResult = service.realizarQuery(new GetFollowers(trino.GetNickPropietario()));

		        	switch(followerResult)
		        	{
			        	case SuccessResult<ArrayList<String>> okGetFollowers -> {
			        		// Se ha recuperado los seguidores del autor del trino
			        		// Por cada seguidor se intenta recuperar el callback correspondiente para enviarle la notificacion
			        		okGetFollowers.data().forEach(username -> {
			        			Optional<CallbackUsuarioInterface> cb = Optional.ofNullable(callbackMap.get(username));
			        			if(cb.isPresent())
			        			{
			        				try {
			        					// Hay que recuperar los datos del usuario para comprobar si está bloqueado
			        					Result <User> userResult = service.realizarQuery(new GetUser(username));

			        					switch (userResult) {
				        					case SuccessResult<User> okGetUser -> {
				        						if (!okGetUser.data().isBanned)
				        						{
				        							// Si no está baneado se llama al callback
				        							cb.get().recibirTrino(trino);
				        						}
				        					}
				        					case ErrorResult errGetUser -> {}
			        					}
									} catch (RemoteException e) {
										// En caso de error podemos deducir que o el callback es inválido o el cliente se ha desconectado
										// En ambos casos eliminamos la entrada en el diccionario
										callbackMap.remove(username);
									}
			        			}
			        		});
			        		return true;
			        	}
			        	case ErrorResult errGetFollowers -> {
			        		return false;
			        	}
		        	}
		        }
		        case ErrorResult err -> {
		        	System.out.println(err.message());
		        	return false;
		        }
	        }

	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
    	return false;
	}

	@Override
	public String listarUsuarios() throws RemoteException {
		try {
	        Registry registry = LocateRegistry.getRegistry("localhost", Basededatos.PORT);
	        ServicioDatosInterface service =
	            (ServicioDatosInterface) registry.lookup(Basededatos.SERVICENAME);

	        Result<ArrayList<String>> result = service.realizarQuery(new GetUsers());

	        switch(result)
	        {
		        case SuccessResult<ArrayList<String>> ok -> {
		        	List<String> users = (List<String>) ok.data();
			        StringBuilder sb = new StringBuilder();
			        users.forEach(username -> sb.append(username).append(System.lineSeparator()));
			        return sb.toString();
		        }
		        case ErrorResult err -> {
		        	return err.message();
		        }
	        }

	    } catch (Exception e) {
	        System.out.println("Error conectando al servidor");
	        e.printStackTrace();
	    }
    	return new String();
	}

	@Override
	public boolean editarSubscripcion(String usernameSource, String usernameTarget, boolean isSubscribed)
			throws RemoteException {
		try {
	        Registry registry = LocateRegistry.getRegistry("localhost", Basededatos.PORT);
	        ServicioDatosInterface service =
	            (ServicioDatosInterface) registry.lookup(Basededatos.SERVICENAME);

	        Result result;
	        if (isSubscribed)
	        {
	        	result = service.ejecutarProcedimiento(new AddFollower(usernameSource, usernameTarget));
	        }
	        else
	        {
	        	result = service.ejecutarProcedimiento(new RemoveFollower(usernameSource, usernameTarget));
	        }

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

	@Override
	public void registrarTrinoCallback(String username, CallbackUsuarioInterface callback) throws RemoteException {
		callbackMap.put(username, callback);
	}

}