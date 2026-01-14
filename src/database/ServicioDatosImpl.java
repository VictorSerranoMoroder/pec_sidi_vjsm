package database;

import java.io.Serializable;
import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import api.ServicioDatosInterface;
import data_model.User;
import data_model.db_requests.*;
import data_model.results.ErrorResult;
import data_model.results.Result;
import data_model.results.SuccessResult;
import data_model.Trino;


public class ServicioDatosImpl extends UnicastRemoteObject implements ServicioDatosInterface {
	private static final long serialVersionUID = 3L;
	private ModeloDatos modeloDatos;

	protected ServicioDatosImpl() throws RemoteException {
        super();
        modeloDatos = new ModeloDatos();
    }

	@Override
	public Result realizarQuery(QueryRequest request) throws RemoteException {
		return switch (request) {
			case GetUser q      -> handleGetUser(q);
	        case GetUsers q     -> handleGetUsers(q);
	        case GetFollowers q -> handleGetFollowers(q);
	        case GetAllTrinos q -> handleGetAllTrinos(q);
	        case GetTrinos q    -> handleGetTrinos(q);
	        case GetFeed q      -> handleGetFeed(q);
		};
	}

	@Override
	public Result ejecutarProcedimiento(ProcedureRequest request) throws RemoteException {
		return switch (request) {
			case AddTrino p		-> handleAddTrino(p);
			case AddUser p		-> handleAddUser(p);
			case AddFollower p  -> handleAddFollower(p);
			case BanUser p		-> handleBanUser(p);
			case UnbanUser p	-> handleUnbanUser(p);
			case RemoveFollower p -> handleRemoveFollower(p);
			case SetOnlineUser p -> handleSetOnlineUser(p);
		};
	}
	
	private Result handleGetUser(GetUser request)
	{
		Optional<User> result = modeloDatos.getUser(request.username());
		if (result.isPresent())
		{
			return new SuccessResult<User>(request, result.get());
		}
		else
		{
			return new ErrorResult(request,"No se pudo encontrar el usuario " + request.username());
		}
	}
	
	private Result handleGetUsers(GetUsers request)
	{
		ArrayList<String> users = new ArrayList<String> (modeloDatos.getUsers());
		if (!users.isEmpty())
		{
			return new SuccessResult<>(request, users);
		}
		else 
		{
			return new ErrorResult(request,"No se pudo crear la lista de usuarios.");
		}
	}
	
	private Result handleGetFollowers(GetFollowers request)
	{
		// Si el opcional contiene un nulo significa que el intento de recuperar los datos ha fallado
		// que un usuario no tenga seguidores no es un fallo, por lo que no debemos de comprobar si la lista está vacía
		Optional<List<String>> followers = modeloDatos.getFollowers(request.username());
		
		if (followers.isPresent())
		{
			return new SuccessResult<ArrayList<String>>(request, (ArrayList<String>)followers.get());
		}
		else 
		{
			return new ErrorResult(request, "Fallo al recuperar los seguidores");
		}
	}
	
	private Result handleGetAllTrinos(GetAllTrinos request)
	{
		ArrayList<Trino> result = new ArrayList<>();
		List<String> users = modeloDatos.getUsers();
		users.forEach(username -> {
			Optional<List<Trino>> resultSet = modeloDatos.getTrinos(username);
			if (resultSet.isPresent())
			{
				result.addAll(resultSet.get());
			}
		});
		return new SuccessResult<ArrayList<Trino>>(request, result);
	}
	
	private Result handleGetTrinos(GetTrinos request)
	{
		return null;
	}
	
	private Result handleGetFeed(GetFeed request)
	{
		Optional<String> feed =modeloDatos.getFeed(request.username());
		if (feed.isPresent())
		{
			return new SuccessResult<>(request, feed.get());
		}
		else
		{
			return new ErrorResult(request,"No se pudo recuperar el feed");
		}
	}
	
	private Result handleAddTrino(AddTrino request)
	{
		boolean success = modeloDatos.addTrino(request.trino().GetNickPropietario(), request.trino().GetTrino());
		
		if (success)
		{
			return new SuccessResult<>(request, true);
		}
		else 
		{
			return new ErrorResult(request, "No se pudo añadir el trino");
		}
	}
	
	private Result handleAddUser(AddUser request)
	{
		if (modeloDatos.addUser(request.user()))
		{
			return new SuccessResult<>(request, true);
		}
		else 
		{
			return new ErrorResult(request, "No se pudo crear el usuario.");
		}
	}
	
	private Result handleAddFollower(AddFollower request)
	{
		boolean success = modeloDatos.addFollower(request.userSource(), request.userTarget());
		
		if (success)
		{
			return new SuccessResult<>(request, true);
		}
		else
		{
			return new ErrorResult(request, "No se pudo completar la operacion");
		}
	}
	
	private Result handleBanUser(BanUser request)
	{
		Optional<User> user = modeloDatos.getUser(request.username());
		if (user.isPresent())
		{
			user.get().isBanned = true;
			return new SuccessResult<>(request, true);
		}
		else 
		{
			return new ErrorResult(request, "No se pudo banear al usuario.");
		}
	}
	
	private Result handleUnbanUser(UnbanUser request)
	{
		Optional<User> user = modeloDatos.getUser(request.username());
		if (user.isPresent())
		{
			user.get().isBanned = false;
			return new SuccessResult<>(request, true);
		}
		else 
		{
			return new ErrorResult(request, "No se pudo unbanear al usuario.");
		}
	}
	
	private Result handleRemoveFollower(RemoveFollower request)
	{
		boolean success = modeloDatos.removeFollower(request.userSource(), request.userTarget());
		
		if (success)
		{
			return new SuccessResult<>(request, true);
		}
		else 
		{
			return new ErrorResult(request, "Error al eliminar subscripcion");
		}
	}
	
	private Result handleSetOnlineUser(SetOnlineUser request)
	{
		Optional<User> user = modeloDatos.getUser(request.username());
		if (user.isPresent())
		{
			if (user.get().isOnline)
			{
				return new ErrorResult(request, "Error, usuario ya autenticado: "+ request.username());
			}
			user.get().isOnline = request.online();
			return new SuccessResult<>(request, null);
		}
		else 
		{
			return new ErrorResult(request, "Error, no se encontró el usuario: "+ request.username());
		}
	}
}

    

