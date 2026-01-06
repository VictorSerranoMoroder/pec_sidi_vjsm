package api;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import data_model.QueryRequest;
import data_model.QueryResult;


public interface ServicioDatosInterface extends Remote {
	public QueryResult<Serializable> realizarQuery(QueryRequest request) throws RemoteException;
	public QueryResult<Serializable> ejecutarProcedimiento(QueryRequest request) throws RemoteException;
}
