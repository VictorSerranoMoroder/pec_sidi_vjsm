package database;

import java.io.Serializable;
import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import api.ServicioDatosInterface;
import data_model.ErrorResult;
import data_model.Result;
import data_model.SuccessResult;
import data_model.User;
import data_model.requests.*;
import data_model.requests.Request;
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
	        case GetTrinos q    -> handleGetTrinos(q);
	        case GetFeed q      -> handleGetFeed(q);
		};
	}

	@Override
	public Result ejecutarProcedimiento(ProcedureRequest request) throws RemoteException {
		return switch (request) {
			case AddTrino p		-> handleAddTrino(p);
			case AddUser p		-> handleAddUser(p);
			case BanUser p		-> handleBanUser(p);
			case UnbanUser p	-> handleUnbanUser(p);
		};
	}
	
	private Result handleGetUser(GetUser request)
	{
		Optional<User> result = modeloDatos.getUser(request.username());
		if (result.isPresent())
		{
			return new SuccessResult<>(request, result.get());
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
		return null;
	}
	
	private Result handleGetTrinos(GetTrinos request)
	{
		return null;
	}
	
	private Result handleGetFeed(GetFeed request)
	{
		return null;
	}
	
	private Result handleAddTrino(AddTrino request)
	{
		return null;
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
	
	private Result handleBanUser(BanUser request)
	{
		return null;
	}
	
	private Result handleUnbanUser(UnbanUser request)
	{
		return null;
	}
}

    

