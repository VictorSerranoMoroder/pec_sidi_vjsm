package database;

import java.io.Serializable;
import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

import api.ServicioDatosInterface;
import data_model.QueryRequest;
import data_model.QueryRequest.QueryType;
import data_model.QueryResult;
import data_model.User;

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
	        case QueryType.GET_USER:
	            Optional<User> result = modeloDatos.getUser(request.usernameFilter);
	            System.out.println(result.isPresent());
	            return new QueryResult<>(request, result);
	        case QueryType.GET_FOLLOWERS:
	            //List<String> f = followers.getOrDefault(req.getUsernameFilter(), new ArrayList<>());
	            return new QueryResult<>(request, Optional.empty());
	
	        case QueryType.GET_TRINOS:
	            //List<Trino> t = trinos.getOrDefault(req.getUsernameFilter(), new ArrayList<>());
	            return new QueryResult<>(request, Optional.empty());
	
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
	        return new QueryResult<>(request, Optional.empty());
	    }
		
		switch (request.procedureType) {
	        case ADD_USER:
	            User u = (User) request.payload;
	            boolean added = modeloDatos.addUser(u);
	            return new QueryResult<>(request, Optional.of(added));
	
	        case REMOVE_USER:
	            //String usernameToRemove = ((User) request.payload).username;
	            //boolean removed = modeloDatos.removeUser(usernameToRemove);
	            //return new QueryResult<>(request, Optional.of(removed));
	
	        case BAN_USER:
	            //String usernameToBan = ((User) request.payload).username;
	            //boolean banned = modeloDatos.banUser(usernameToBan);
	            //return new QueryResult<>(request, Optional.of(banned));
	
	        case ADD_TRINO:
	            //Trino t = (Trino) request.payload;
	            //boolean trinoAdded = modeloDatos.addTrino(t);
	            //return new QueryResult<>(request, Optional.of(trinoAdded));
	        default:
	            return new QueryResult<>(request, Optional.empty());
	    }
	}
		
}

    

