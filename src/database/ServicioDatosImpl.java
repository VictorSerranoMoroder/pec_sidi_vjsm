package database;

import java.io.Serializable;
import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import api.ServicioDatosInterface;
import data_model.QueryRequest;
import data_model.QueryRequest.ProcedureType;
import data_model.QueryRequest.QueryType;
import data_model.QueryResult;
import data_model.User;
import data_model.Trino;

public class ServicioDatosImpl extends UnicastRemoteObject implements ServicioDatosInterface {
	private static final long serialVersionUID = 3L;
	private ModeloDatos modeloDatos;

	protected ServicioDatosImpl() throws RemoteException {
        super();
        modeloDatos = new ModeloDatos();
    }

	@Override
	public QueryResult realizarQuery(QueryRequest request) throws RemoteException {
		switch (request.queryType) {
	        case QueryType.GET_USER: {
	        	User user = (User) request.payload;
	        	boolean success = authUser(user);
	            return new QueryResult<>(request, success);
	        }
	        case QueryType.GET_FOLLOWERS: {
	        	String username = (String) request.payload;
	            Optional<List<String>> result = modeloDatos.getSubscriptions(username);
	            return new QueryResult<>(
	            		request, 
	            		result.isPresent(), 
	            		// ArrayList<String> is Serializable 
	            		(Serializable) result.orElse(null));
	        }
	        case QueryType.GET_TRINOS: {
	        	String username = (String) request.payload;
	            Optional<List<Trino>> result = modeloDatos.getTrinos(username);
	            return new QueryResult<>(
	            		request, 
	            		result.isPresent(), 
	            		// ArrayList<String> is Serializable 
	            		(Serializable) result.orElse(null));
	        }
	        case QueryType.GET_FEED:
	            // For example, combine trinos of all users followed
	            //List<Trino> feed = new ArrayList<>();
	            //for (String followed : followers.getOrDefault(req.getUsernameFilter(), List.of())) {
	            //    feed.addAll(trinos.getOrDefault(followed, List.of()));
	        	return new QueryResult<>(request, Optional.empty());
	        default:
	        	return new QueryResult<>(request, Optional.empty());
	        }
    }

	@Override
	public QueryResult<Serializable> ejecutarProcedimiento(QueryRequest request) throws RemoteException {
		if (request.procedureType == null || request.payload == null) {
	        return new QueryResult<>(request, false, null);
	    }
		
		boolean success = false;
		
		switch (request.procedureType) {
	        case ADD_USER: {
	            User userToAdd = (User) request.payload;
	            success = modeloDatos.addUser(userToAdd);
	            return new QueryResult<>(request, success);
	        }
	        case REMOVE_USER: {
	        	User userToRemove = (User) request.payload;
	            success = modeloDatos.removeUser(userToRemove);
	            return new QueryResult<>(request, success);
	        }
	        case BAN_USER:
	        case UNBAN_USER: {
	            String usernameToSetBan = (String) request.payload;
	            Optional<User> userToSetBan = modeloDatos.getUser(usernameToSetBan);
	            if (!userToSetBan.isPresent())
	            {
	            	return new QueryResult<>(request, false);
	            }
	            userToSetBan.get().setBan(request.procedureType == ProcedureType.BAN_USER ? true : false);
	            success = modeloDatos.editUser(userToSetBan.get());
	            return new QueryResult<>(request, success);
	        }
	        case ADD_TRINO:
	            //Trino t = (Trino) request.payload;
	            //boolean trinoAdded = modeloDatos.addTrino(t);
	            //return new QueryResult<>(request, Optional.of(trinoAdded));
	        default:
	            return new QueryResult<>(request, Optional.empty());
	    }
	}
	
	private boolean authUser(User usuario)
	{
		Optional<User> result = modeloDatos.getUser(usuario.username);
		
		if (!result.isPresent())
		{
			return false;
		}
		
		// Check if credentials are correct
		if (usuario.username.equals(result.get().username) 
				&& usuario.password.equals(result.get().password))
		{
			return true;
		}
		return false;
	}
		
}

    

