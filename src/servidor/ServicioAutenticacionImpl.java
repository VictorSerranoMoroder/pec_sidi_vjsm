/*
 * ServicioAutenticacionImpl.java
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

import api.ServicioAutenticacionInterface;
import api.ServicioDatosInterface;
import data_model.User;
import data_model.db_requests.AddUser;
import data_model.db_requests.GetUser;
import data_model.db_requests.SetOnlineUser;
import data_model.results.ErrorResult;
import data_model.results.Result;
import data_model.results.SuccessResult;
import data_model.svr_requests.AuthenticateUser;
import data_model.svr_requests.SignUpRequest;
import database.Basededatos;

public class ServicioAutenticacionImpl extends UnicastRemoteObject implements ServicioAutenticacionInterface {
	private static final long serialVersionUID = 3L;

	public ServicioAutenticacionImpl() throws RemoteException {
        super();
    }

    @Override
    public Result AutenticarUsuario(AuthenticateUser request) throws RemoteException {
    	try {
	        Registry registry = LocateRegistry.getRegistry("localhost", Basededatos.PORT);
	        ServicioDatosInterface service =
	            (ServicioDatosInterface) registry.lookup(Basededatos.SERVICENAME);


	        Result<User> result = service.realizarQuery(new GetUser(request.username()));

	        switch(result)
	        {
		        case SuccessResult<User> ok -> {
		        	User data = ok.data();
		        	// Si las credenciales son correctas, loguear
		        	if (request.username().equals(data.username) && request.password().equals(data.password))
		        	{
		        		// Al loguear hay que cambiar el estado del usuario a conectado
		        		Result logInResult = service.ejecutarProcedimiento(new SetOnlineUser(request.username(), true));
		        		return logInResult;
		        	}
		        	else
		        	{
		        		return new ErrorResult(request, "Credenciales incorrectas");
		        	}
		        }
		        case ErrorResult err -> {
		        	return new ErrorResult(request, "Fallo al adquirir Usuario.");
		        }
	        }
	    } catch (Exception e) {
	    	return new ErrorResult(request, "Error de conexion");
	    }
    }

	@Override
	public Result RegistrarUsuario(SignUpRequest request) throws RemoteException {
    	try {
	        Registry registry = LocateRegistry.getRegistry("localhost", 45002);
	        ServicioDatosInterface service =
	            (ServicioDatosInterface) registry.lookup("ServicioDatos");

	        Result result = service.ejecutarProcedimiento(new AddUser(new User(request.username(), request.password())));
	        return result;
	    } catch (Exception e) {
	        return new ErrorResult(request, "Error de conexion al servidor");
	    }
	}
}
